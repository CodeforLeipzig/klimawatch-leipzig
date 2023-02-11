package de.l.oklab.klimawatch.catalog.processors

import de.l.oklab.klimawatch.catalog.to.*
import de.l.oklab.klimawatch.catalog.visitors.LevelAwareProcessorContext

class PrintlnProcessor : CatalogElementProcessor<LevelAwareProcessorContext> {

    override fun process(element: CatalogElement, context: LevelAwareProcessorContext) {
        val type = element::class.simpleName
        val content = getTitle(element)
        serialize(context.level, type, content)
    }

    private fun getTitle(element: CatalogElement) =
        when (element) {
            is Organization -> element.label
            is Catalog -> element.label
            is ConceptScheme -> element.prefLabel
            is Concept -> element.prefLabel
            is DataSet -> "${element.title}: ${element.getResolvedAccrualPeriodicity()}"
            is Distribution -> "${element.title}: ${element.downloadURL}"
            else -> null
        }


    private fun serialize(level: Int, type: String?, content: String?) {
        println("${leading(level)} * $type: $content")
    }

    private fun leading(level: Int) = (0..level).joinToString("") { " " }
}