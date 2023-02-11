package de.l.oklab.klimawatch.catalog.serializer

import de.l.oklab.klimawatch.catalog.processors.CatalogElementProcessor
import de.l.oklab.klimawatch.catalog.processors.PrintlnProcessor
import de.l.oklab.klimawatch.catalog.processors.ProcessorContext
import de.l.oklab.klimawatch.catalog.to.*
import de.l.oklab.klimawatch.catalog.visitors.*

data class SerializationContext(
    val objectRegistry: ObjectRegistry,
    val visitors: MutableMap<Types, CatalogElementVisitor<*, *>> = mutableMapOf(),
    val processors: MutableMap<Types, CatalogElementProcessor<*>> = mutableMapOf(),
) {

    fun visit(element: CatalogElement, context: VisitorContext) {
        val visitor = visitors[element.getResolvedType()]
        when (context) {
            is LevelAwareVisitorContext -> when (visitor) {
                is CatalogVisitor -> when (element) {
                    is Catalog -> visitor.visit(element, context)
                }

                is ConceptSchemeVisitor -> when (element) {
                    is ConceptScheme -> visitor.visit(element, context)
                }

                is ConceptVisitor -> when (element) {
                    is Concept -> visitor.visit(element, context)
                }

                is DatasetVisitor -> when (element) {
                    is DataSet -> visitor.visit(element, context)
                }

                is DistributionVisitor -> when (element) {
                    is Distribution -> visitor.visit(element, context)
                }
            }
        }
    }

    fun process(element: CatalogElement, context: ProcessorContext) {
        val processor = processors[element.getResolvedType()]
        when (context) {
            is LevelAwareProcessorContext -> when (processor) {
                is PrintlnProcessor -> processor.process(element, context)
            }
        }
    }
}