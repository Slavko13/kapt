package com.demo

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import org.slf4j.LoggerFactory
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class Generator: AbstractProcessor() {

    private val logger = LoggerFactory.getLogger(Generator::class.java)

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(MyConstant::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported();
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val elements = roundEnv?.getElementsAnnotatedWith(MyConstant::class.java)
        logger.info("Processing annotations: {}", elements)
        val packageName = "com.demo"
        val fileName = "constansta"
        val file = FileSpec.builder(packageName, fileName).build();
        val generatedDirectory = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(generatedDirectory, "$fileName.kt"))

        return true;
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


}
