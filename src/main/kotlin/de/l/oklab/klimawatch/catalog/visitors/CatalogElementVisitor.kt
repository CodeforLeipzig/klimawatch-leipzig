package de.l.oklab.klimawatch.catalog.visitors

import de.l.oklab.klimawatch.catalog.to.CatalogElement

interface CatalogElementVisitor<in T: CatalogElement, in U: VisitorContext> {

    fun visit(element: T, visitContext: U)
}

open class DefaultVisitorContext(val values: Map<String, Any>): VisitorContext

interface VisitorContext