package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.curriculum.CurriculumData
import com.example.data.curriculum.ProjectsData
import com.example.data.model.LevelSystem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HTML Quest", appName)
    }

    @Test
    fun `verify 10 curriculum units exist with valid lessons and exercises`() {
        assertEquals(10, CurriculumData.units.size)

        CurriculumData.units.forEach { unit ->
            assertTrue("Unit ${unit.id} should have lessons", unit.lessons.isNotEmpty())
            unit.lessons.forEach { lesson ->
                assertTrue("Lesson ${lesson.id} should have exercises", lesson.exercises.isNotEmpty())
            }
        }
    }

    @Test
    fun `verify level progression rules`() {
        val beginner = LevelSystem.getLevelForXp(0)
        assertEquals(1, beginner.levelNumber)
        assertEquals("HTML Beginner", beginner.title)

        val explorer = LevelSystem.getLevelForXp(200)
        assertEquals(2, explorer.levelNumber)
        assertEquals("HTML Explorer", explorer.title)

        val master = LevelSystem.getLevelForXp(3000)
        assertEquals(6, master.levelNumber)
        assertEquals("HTML Master", master.title)
    }

    @Test
    fun `verify project evaluation logic`() {
        val profileProj = ProjectsData.getProjectById("proj_profile")
        assertNotNull(profileProj)

        // Passing code
        val goodCode = """
            <!DOCTYPE html>
            <html>
            <body>
              <h1>My Name</h1>
              <p>Intro paragraph</p>
              <img src="pic.jpg" alt="Photo">
              <ul><li>Coding</li></ul>
            </body>
            </html>
        """.trimIndent()

        val (passed, checks) = profileProj!!.evaluate(goodCode)
        assertTrue("Project should pass with all required tags", passed)
        assertTrue(checks.all { it.second })
    }
}
