package de.l.oklab.klimawatch.emissions

import de.l.oklab.klimawatch.catalog.CatalogReadService
import de.l.oklab.klimawatch.catalog.serializer.ConceptsHierarchySerializer
import org.junit.jupiter.api.Test
import java.io.File

class JsonLdTest {

    @Test
    fun readJsonLd() {
        //val file = File("C:/Users/Joerg/Desktop/statistik-katalog.json")
        val file = File("/home/joerg/Schreibtisch/statistik-katalog.json")
        val objectRegistry = CatalogReadService().readCatalog(file.toURI())
        ConceptsHierarchySerializer(objectRegistry).serialize()
    }
}



