package de.l.oklab.klimawatch.catalog.to

data class ObjectRegistry(
    val concepts: MutableMap<String, Concept> = mutableMapOf(),
    val conceptSchemes: MutableMap<String, ConceptScheme> = mutableMapOf(),
    val catalogs: MutableMap<String, Catalog> = mutableMapOf(),
    val datasets: MutableMap<String, DataSet> = mutableMapOf(),
    val distributions: MutableMap<String, Distribution> = mutableMapOf(),
    val organizations: MutableMap<String, Organization> = mutableMapOf()
)