package de.l.oklab.klimawatch.catalog.visitors

import de.l.oklab.klimawatch.catalog.processors.ProcessorContext
import de.l.oklab.klimawatch.catalog.serializer.SerializationContext
import de.l.oklab.klimawatch.catalog.to.*


class LevelAwareVisitorContext(val level: Int) : VisitorContext

class LevelAwareProcessorContext(val level: Int) : ProcessorContext


class CatalogVisitor(private val context: SerializationContext) :
    CatalogElementVisitor<Catalog, LevelAwareVisitorContext> {

    override fun visit(element: Catalog, visitContext: LevelAwareVisitorContext) {
        context.process(element, LevelAwareProcessorContext(visitContext.level))
        element.getResolvedThemeTaxonomy(context.objectRegistry)?.let {
            context.visit(it, LevelAwareVisitorContext(visitContext.level + 2))
        }
    }
}

class ConceptSchemeVisitor(private val context: SerializationContext) :
    CatalogElementVisitor<ConceptScheme, LevelAwareVisitorContext> {

    override fun visit(element: ConceptScheme, visitContext: LevelAwareVisitorContext) {
        context.process(element, LevelAwareProcessorContext(visitContext.level))
        element.getResolvedHasTopConcept(context.objectRegistry)?.let { topConcept ->
            context.visit(topConcept, LevelAwareVisitorContext(visitContext.level + 2))
        }
    }
}

class ConceptVisitor(private val context: SerializationContext) :
    CatalogElementVisitor<Concept, LevelAwareVisitorContext> {

    override fun visit(element: Concept, visitContext: LevelAwareVisitorContext) {
        context.process(element, LevelAwareProcessorContext(visitContext.level))
        val innerlevel = visitContext.level + 2
        for (narrower in element.getResolvedNarrower(context.objectRegistry)) {
            context.visit(narrower, LevelAwareVisitorContext(innerlevel))
        }
        for (dataset in element.getResolvedDataSets(context.objectRegistry)) {
            context.visit(dataset, LevelAwareVisitorContext(innerlevel))
        }
    }
}

class DatasetVisitor(private val context: SerializationContext) :
    CatalogElementVisitor<DataSet, LevelAwareVisitorContext> {

    override fun visit(element: DataSet, visitContext: LevelAwareVisitorContext) {
        context.process(element, LevelAwareProcessorContext(visitContext.level))
        val newVisitContext = LevelAwareVisitorContext(visitContext.level + 2)
        for (dist in element.getResolvedDistributions(context.objectRegistry)) {
            context.visit(dist, newVisitContext)
        }
    }
}

class DistributionVisitor(private val context: SerializationContext) :
    CatalogElementVisitor<Distribution, LevelAwareVisitorContext> {

    override fun visit(element: Distribution, visitContext: LevelAwareVisitorContext) {
        context.process(element, LevelAwareProcessorContext(visitContext.level))
    }
}