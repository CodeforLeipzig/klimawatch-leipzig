package de.l.oklab.klimawatch.catalog.serializer

import de.l.oklab.klimawatch.catalog.processors.PrintlnProcessor
import de.l.oklab.klimawatch.catalog.to.ObjectRegistry
import de.l.oklab.klimawatch.catalog.to.Types
import de.l.oklab.klimawatch.catalog.visitors.*

class ConceptsHierarchySerializer(val objectRegistry: ObjectRegistry) {

    fun serialize() {
        val context = SerializationContext(objectRegistry)
        val genericProcessor = PrintlnProcessor()
        context.visitors.putAll(mapOf(
            Types.Concept to ConceptVisitor(context),
            Types.ConceptScheme to ConceptSchemeVisitor(context),
            Types.Catalog to CatalogVisitor(context),
            Types.Dataset to DatasetVisitor(context),
            Types.Distribution to DistributionVisitor(context)
        ))
        context.processors.putAll(mapOf(
            Types.Concept to genericProcessor,
            Types.ConceptScheme to genericProcessor,
            Types.Catalog to genericProcessor,
            Types.Dataset to genericProcessor,
            Types.Distribution to genericProcessor
        ))
        for (catalog in context.objectRegistry.catalogs.values.sortedBy { it.title }) {
            context.visit(catalog, LevelAwareVisitorContext(0))
        }
    }
}