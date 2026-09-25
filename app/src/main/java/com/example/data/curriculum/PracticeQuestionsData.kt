package com.example.data.curriculum

import com.example.data.model.Exercise
import com.example.data.model.ExerciseType

object PracticeQuestionsData {

    val topics = listOf(
        "Basics",
        "Text",
        "Links",
        "Images",
        "Semantic HTML",
        "Tables",
        "Forms",
        "Attributes",
        "Accessibility"
    )

    private val questionsPool: Map<String, List<Exercise>> = mapOf(
        "Basics" to listOf(
            Exercise(
                id = "prac_b1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which declaration is required at the very top of modern HTML5 documents?",
                options = listOf("<!DOCTYPE html>", "<html5>", "<?xml version=\"1.0\"?>", "<DOCTYPE HTML5>"),
                correctAnswer = "<!DOCTYPE html>",
                solutionExplanation = "<!DOCTYPE html> informs browsers to render the page in standard HTML5 mode.",
                conceptTag = "Basics"
            ),
            Exercise(
                id = "prac_b2",
                type = ExerciseType.FILL_BLANK,
                prompt = "Close the root HTML tag:",
                blanksPrefix = "<html>\n  <body></body>\n",
                blanksSuffix = "",
                options = listOf("</html>", "</body>", "</head>", "</root>"),
                correctAnswer = "</html>",
                solutionExplanation = "The <html> element is closed at the very end of the file with </html>.",
                conceptTag = "Basics"
            ),
            Exercise(
                id = "prac_b3",
                type = ExerciseType.OUTPUT_PREDICTION,
                prompt = "What does this comment render on the screen?",
                codeSnippet = "<!-- This is a secret message -->\n<p>Hello World</p>",
                options = listOf("Hello World", "This is a secret message", "Both text lines", "Nothing"),
                correctAnswer = "Hello World",
                solutionExplanation = "HTML comments <!-- ... --> are ignored by the browser and are never rendered to users.",
                conceptTag = "Basics"
            ),
            Exercise(
                id = "prac_b4",
                type = ExerciseType.CODE_CHALLENGE,
                prompt = "Write a complete paragraph tag that says 'I love HTML'.",
                starterCode = "<!-- Write paragraph here -->\n",
                expectedTag = "p",
                evaluationRules = listOf("p", "I love HTML"),
                solutionExplanation = "Write <p>I love HTML</p> to pass this practice challenge!",
                conceptTag = "Basics"
            )
        ),
        "Text" to listOf(
            Exercise(
                id = "prac_t1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which tag defines an ordered (numbered) list?",
                options = listOf("<ol>", "<ul>", "<dl>", "<list>"),
                correctAnswer = "<ol>",
                solutionExplanation = "<ol> stands for 'ordered list' and displays numbers (1, 2, 3...).",
                conceptTag = "Text"
            ),
            Exercise(
                id = "prac_t2",
                type = ExerciseType.FIND_MISTAKE,
                prompt = "Find the mistake in this list:",
                codeSnippet = "<ul>\n  <p>Item 1</p>\n  <p>Item 2</p>\n</ul>",
                options = listOf(
                    "Direct children of <ul> should be <li> elements, not <p>",
                    "<ul> requires an ordered list number",
                    "Missing closing tag",
                    "No mistake"
                ),
                correctAnswer = "Direct children of <ul> should be <li> elements, not <p>",
                solutionExplanation = "Only <li> (list item) elements are valid direct children of <ul> and <ol>.",
                conceptTag = "Text"
            ),
            Exercise(
                id = "prac_t3",
                type = ExerciseType.CODE_ORDERING,
                prompt = "Sort headings from highest importance to lowest:",
                correctOrder = listOf("<h1>", "<h2>", "<h3>", "<h4>"),
                solutionExplanation = "Heading levels descend from <h1> (highest) to <h6> (lowest).",
                conceptTag = "Text"
            )
        ),
        "Links" to listOf(
            Exercise(
                id = "prac_l1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "How do you link to an element with id='contact' on the same page?",
                options = listOf("href=\"#contact\"", "href=\"contact\"", "href=\"@contact\"", "link=\"contact\""),
                correctAnswer = "href=\"#contact\"",
                solutionExplanation = "The hash symbol (#) indicates an in-page anchor linking to an id attribute.",
                conceptTag = "Links"
            ),
            Exercise(
                id = "prac_l2",
                type = ExerciseType.FILL_BLANK,
                prompt = "Fill in the attribute for an email mailto link:",
                blanksPrefix = "<a ",
                blanksSuffix = "=\"mailto:support@quest.com\">Email Us</a>",
                options = listOf("href", "src", "action", "to"),
                correctAnswer = "href",
                solutionExplanation = "Mailto links use href=\"mailto:address@domain.com\".",
                conceptTag = "Links"
            )
        ),
        "Images" to listOf(
            Exercise(
                id = "prac_i1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which of the following is true about the <img> tag?",
                options = listOf(
                    "It is a void (self-closing) element and does not have a </img> closing tag",
                    "It must be closed with </img>",
                    "It cannot have dimensions defined in HTML",
                    "It only supports PNG format"
                ),
                correctAnswer = "It is a void (self-closing) element and does not have a </img> closing tag",
                solutionExplanation = "<img> is an empty/void tag; in HTML5, writing </img> is invalid.",
                conceptTag = "Images"
            ),
            Exercise(
                id = "prac_i2",
                type = ExerciseType.CODE_CHALLENGE,
                prompt = "Add an image with src='avatar.jpg' and width='100'.",
                starterCode = "<!-- Write img tag here -->\n",
                expectedTag = "img",
                evaluationRules = listOf("img", "src", "avatar.jpg", "width"),
                solutionExplanation = "<img src=\"avatar.jpg\" width=\"100\" alt=\"Avatar\">",
                conceptTag = "Images"
            )
        ),
        "Semantic HTML" to listOf(
            Exercise(
                id = "prac_s1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which semantic tag should contain website navigation links?",
                options = listOf("<nav>", "<menu>", "<links>", "<navigate>"),
                correctAnswer = "<nav>",
                solutionExplanation = "<nav> represents a section of a page whose purpose is to provide navigation links.",
                conceptTag = "Semantic HTML"
            ),
            Exercise(
                id = "prac_s2",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "What is the primary benefit of using semantic tags over generic <div> tags?",
                options = listOf(
                    "Better accessibility for screen readers and improved SEO for search engines",
                    "Makes the page download twice as fast",
                    "Enables 3D animations automatically",
                    "Prevents CSS errors"
                ),
                correctAnswer = "Better accessibility for screen readers and improved SEO for search engines",
                solutionExplanation = "Semantic tags provide meaning so search engines and assistive technology can interpret page structure accurately.",
                conceptTag = "Semantic HTML"
            )
        ),
        "Tables" to listOf(
            Exercise(
                id = "prac_tb1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which element is used to group the body content in an HTML table?",
                options = listOf("<tbody>", "<tcontent>", "<tr>", "<main>"),
                correctAnswer = "<tbody>",
                solutionExplanation = "<tbody> groups the main content body rows of an HTML table.",
                conceptTag = "Tables"
            ),
            Exercise(
                id = "prac_tb2",
                type = ExerciseType.CODE_CHALLENGE,
                prompt = "Create a table row (tr) with two data cells (td) containing 'Item' and '$10'.",
                starterCode = "<table>\n  <!-- Create your row here -->\n</table>",
                expectedTag = "tr",
                evaluationRules = listOf("tr", "td", "Item", "10"),
                solutionExplanation = "<tr><td>Item</td><td>$10</td></tr>",
                conceptTag = "Tables"
            )
        ),
        "Forms" to listOf(
            Exercise(
                id = "prac_f1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which input type lets users pick a date from a calendar picker?",
                options = listOf("type=\"date\"", "type=\"calendar\"", "type=\"day\"", "type=\"timepicker\""),
                correctAnswer = "type=\"date\"",
                solutionExplanation = "<input type=\"date\"> invokes native browser calendar pickers.",
                conceptTag = "Forms"
            ),
            Exercise(
                id = "prac_f2",
                type = ExerciseType.FILL_BLANK,
                prompt = "Fill in the required attribute to prevent form submission if empty:",
                blanksPrefix = "<input type=\"text\" name=\"username\" ",
                blanksSuffix = ">",
                options = listOf("required", "mandatory", "validate", "locked"),
                correctAnswer = "required",
                solutionExplanation = "The 'required' boolean attribute mandates that an input must be filled out before submitting.",
                conceptTag = "Forms"
            )
        ),
        "Attributes" to listOf(
            Exercise(
                id = "prac_a1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "Which attribute displays a small tooltip when the mouse hovers over an element?",
                options = listOf("title", "tooltip", "hover", "alt"),
                correctAnswer = "title",
                solutionExplanation = "The global 'title' attribute offers advisory information displayed as a native tooltip.",
                conceptTag = "Attributes"
            )
        ),
        "Accessibility" to listOf(
            Exercise(
                id = "prac_ac1",
                type = ExerciseType.MULTIPLE_CHOICE,
                prompt = "When an image is purely decorative and adds no meaning, what should the alt attribute be?",
                options = listOf("alt=\"\" (empty alt)", "Omit the alt attribute entirely", "alt=\"decorative image\"", "alt=\"none\""),
                correctAnswer = "alt=\"\" (empty alt)",
                solutionExplanation = "An empty alt=\"\" signals screen readers to ignore the decorative image cleanly, whereas omitting alt causes screen readers to read the file URL!",
                conceptTag = "Accessibility"
            )
        )
    )

    fun getQuestionsForTopic(topic: String): List<Exercise> {
        return questionsPool[topic] ?: emptyList()
    }

    fun getAllQuestions(): List<Exercise> {
        return questionsPool.values.flatten()
    }
}
