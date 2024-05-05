package org.ddf.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.ddf.utils.Log;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {
    /**
     * Sets the retry analyzer for test annotations.
     *
     * @param annotation The test annotation to be transformed.
     * @param testClass The test class.
     * @param testConstructor The test constructor.
     * @param testMethod The test method.
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Set Retry class as the retry analyzer for the test annotation
        try {
            annotation.setRetryAnalyzer(Retry.class);
        } catch (Exception e) {
            // Handle exceptions if Retry class cannot be set as retry analyzer
            Log.error("Failed to set Retry class as retry analyzer: " + e.getMessage());
        }
    }
}
