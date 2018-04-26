package com.ragaisis.sunday.dagger

import dagger.MapKey
import kotlin.reflect.KClass
import android.arch.lifecycle.ViewModel

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)