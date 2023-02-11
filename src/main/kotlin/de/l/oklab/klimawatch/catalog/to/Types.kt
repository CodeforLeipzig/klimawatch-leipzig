package de.l.oklab.klimawatch.catalog.to

enum class Types(val namespace: String) {
    Concept("http://www.w3.org/2004/02/skos/core#Concept"),
    ConceptScheme("http://www.w3.org/2004/02/skos/core#ConceptScheme"),
    Catalog("http://www.w3.org/ns/dcat#Catalog"),
    Dataset("http://www.w3.org/ns/dcat#Dataset"),
    Distribution("http://www.w3.org/ns/dcat#Distribution"),
    Organization("http://xmlns.com/foaf/0.1/Organization");

    companion object {
        fun from(key: String): Types? = values().firstOrNull { it.namespace == key }
    }
}

enum class AccrualPeriodicity(val suffix: String) {
    YEARLY("A"), HALFYEAR("S"), QUARTERLY("Q"), MONTHLY("M"), WEEKLY("W"), DAILY("D");

    companion object {
        fun from(fqn: String) = values().firstOrNull { fqn.endsWith("#freq-${it.suffix}") }
    }
}

enum class Language(val code: String) {
    DE("de");

    companion object {
        fun from(fqn: String) {
            Language.values().firstOrNull { fqn.endsWith("/iso639-1/${it.code}") }
        }
    }
}
