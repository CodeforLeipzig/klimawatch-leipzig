package de.l.oklab.klimawatch.inhabitant

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class InhabitantService @Autowired constructor(internal val restTemplate: RestTemplate) {

    fun downloadInhabitantData() {
        //restTemplate.
    }
}