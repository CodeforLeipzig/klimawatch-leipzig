package de.l.oklab.klimawatch.emissions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension::class)
class EmissionsSaveTest {

    @Autowired
    lateinit var emissionsService: EmissionsService

    @Test
    fun testSave() {
        emissionsService.importData()
    }
}