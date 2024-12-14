package todolist.security

import jakarta.ws.rs.NameBinding

@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Secured
