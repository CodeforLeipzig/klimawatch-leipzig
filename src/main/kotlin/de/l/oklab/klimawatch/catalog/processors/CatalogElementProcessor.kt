package de.l.oklab.klimawatch.catalog.processors

import de.l.oklab.klimawatch.catalog.to.CatalogElement

interface CatalogElementProcessor<T: ProcessorContext> {

    fun process(element: CatalogElement, context: T)
}

open class DefaultProcessorContext(val values: Map<String, Any>): ProcessorContext

interface ProcessorContext