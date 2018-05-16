package com.honsul.inthewood.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpiderParser
public @interface BookingParser {
  @AliasFor(annotation = Component.class)
  String value() default "";
  
  @AliasFor(annotation = SpiderParser.class)
  String resortId() default "";

}
