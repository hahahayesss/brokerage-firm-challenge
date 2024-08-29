package io.atk.brokeragefirmchallenge.security.model;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#accountId == authentication.name or hasRole('ROLE_ADMIN')")
public @interface OwnOnly {
}
