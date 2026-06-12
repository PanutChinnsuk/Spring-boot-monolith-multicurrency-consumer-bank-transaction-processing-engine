package com.project.banksystem.application.aspect.auditing;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.auditlog.body.AuditLogContext;
import com.project.banksystem.application.backend.auditlog.body.AuditableBody;
import com.project.banksystem.application.backend.auditlog.internal.InternalAuditLogCreateService;
import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.application.aspect.auditing.anno.methodtrigger.Audit;
import com.project.banksystem.application.aspect.auditing.anno.parameteridentifier.BodyWithId;
import com.project.banksystem.application.aspect.auditing.anno.parameteridentifier.PureId;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;


@Aspect
@Component
public class AuditLogAspect {
    private final InternalAuditLogCreateService internalAuditLogCreateService;

    public AuditLogAspect(InternalAuditLogCreateService internalAuditLogCreateService) {
        this.internalAuditLogCreateService = internalAuditLogCreateService;
    }

    private void buildAuditLog(final String action,
                               final String entity,
                               final boolean success,
                               final String entityId) {
        internalAuditLogCreateService.audit(new AuditLogContext(null),
                action,
                entity,
                success,
                entityId);
    }

    private Set<Class> auditableType = Set.of(
            UUID.class,
            Long.class
    );

    private String getEntityId(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Annotation[][] annotations = method.getParameterAnnotations();
        Object[] parameters = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            Object object = parameters[i];

            for (int j = 0; j < annotations[i].length; j++) {
                Annotation annotation = annotations[i][j];
                if (annotation instanceof PureId) {
                    if (auditableType.contains(object.getClass())) {
                        return String.valueOf(object);
                    }
                } else if (annotation instanceof BodyWithId) {
                    if (object instanceof AuditableBody body) {
                        return body.getIdAsString();
                    }
                }
            }
        }

        return "FAILED_TO_GET_ENTITY_ID" + method.getName();
    }

    @Around("@annotation(audit)")
    public Object auditAspect(ProceedingJoinPoint joinPoint,
                              Audit audit) throws Throwable {
        boolean alwaysAudit = audit.alwaysAudit();

        if (!alwaysAudit && !CurrentUser.requiredAuditing()) {
            return joinPoint.proceed();
        }

        String action = audit.action();
        String entity = audit.entity();
        String entityId = getEntityId(joinPoint);


        try {
            Object object = joinPoint.proceed();

            SpringTransactionSynchronization.runAfterCommit(
                    () -> buildAuditLog(action, entity, true, entityId)
            );

            return object;
        }catch (Throwable e) {
            SpringTransactionSynchronization.runOnFailedCommit(
                    () -> buildAuditLog(action, entity, false, entityId)
            );

            throw e;
        }

    }
}
