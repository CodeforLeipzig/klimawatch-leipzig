package de.l.oklab.klimawatch.catalog

import com.apicatalog.jsonld.JsonLd
import de.l.oklab.klimawatch.catalog.to.*
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CatalogReadService {

    fun readCatalog(uri: URI): ObjectRegistry {
        val objectRegistry = ObjectRegistry()
        val statisticCatalog = JsonLd.expand(uri)
            .ordered()
            .get()
        val iter = statisticCatalog.iterator()

        while (iter.hasNext()) {
            val next = iter.next().asJsonObject()
            val key: String? = singleValue(next["@type"])
            when (val type = key?.let { Types.from(key) }) {
                Types.Catalog -> {
                    val catalog = Catalog().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.catalogs[catalog.id!!] = catalog
                }

                Types.Concept -> {
                    val concept = Concept().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.concepts[concept.id!!] = concept
                }

                Types.ConceptScheme -> {
                    val conceptScheme = ConceptScheme().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.conceptSchemes[conceptScheme.id!!] = conceptScheme
                }

                Types.Dataset -> {
                    val dataSet = DataSet().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.datasets[dataSet.id!!] = dataSet
                }

                Types.Distribution -> {
                    val distribution = Distribution().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.distributions[distribution.id!!] = distribution
                }

                Types.Organization -> {
                    val organization = Organization().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    objectRegistry.organizations[organization.id!!] = organization
                }

                else -> println("missed type $type")
            }
        }
        return objectRegistry
    }
}