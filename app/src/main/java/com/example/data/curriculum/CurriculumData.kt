package com.example.data.curriculum

import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.LearningUnit
import com.example.data.model.Lesson

object CurriculumData {

    val units: List<LearningUnit> = listOf(
        // UNIT 1: HTML Basics
        LearningUnit(
            id = 1,
            title = "Unit 1: HTML Basics",
            subtitle = "The Foundation of the Web",
            description = "Discover what HTML is, understand the skeleton of every website, and write your first valid HTML tags!",
            iconName = "foundation",
            lessons = listOf(
                Lesson(
                    id = "u1_l1",
                    unitId = 1,
                    title = "What is HTML?",
                    subtitle = "The building blocks of webpages",
                    iconName = "school",
                    exercises = listOf(
                        Exercise(
                            id = "u1_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Welcome to HTML Quest!",
                            explanation = "HTML stands for HyperText Markup Language. It is NOT a programming language, but rather a markup language that gives structure and meaning to web content. Browsers read HTML to know what to display on the screen.",
                            codeSnippet = "<p>HTML makes the web work!</p>",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "What does the acronym HTML stand for?",
                            options = listOf(
                                "HyperText Markup Language",
                                "High Text Machine Language",
                                "Hyperlink and Text Management Language",
                                "Home Tool Multi Language"
                            ),
                            correctAnswer = "HyperText Markup Language",
                            solutionExplanation = "HTML stands for HyperText Markup Language. It marks up text to tell browsers how content should be structured!",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l1_e3",
                            type = ExerciseType.OUTPUT_PREDICTION,
                            prompt = "What will the browser display when reading this tag?",
                            codeSnippet = "<p>Welcome Adventurer!</p>",
                            options = listOf(
                                "Welcome Adventurer!",
                                "<p>Welcome Adventurer!</p>",
                                "A blank screen",
                                "Error: missing script"
                            ),
                            correctAnswer = "Welcome Adventurer!",
                            solutionExplanation = "Browsers render the inner text between opening and closing tags. The <p> tags tell the browser it is a paragraph.",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l1_e4",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Complete the closing tag for a paragraph:",
                            blanksPrefix = "<p>Hello World",
                            blanksSuffix = "",
                            options = listOf("</p>", "<p>", "</div>", "</span>"),
                            correctAnswer = "</p>",
                            solutionExplanation = "Most HTML elements require a closing tag with a forward slash '/' before the tag name, like </p>.",
                            conceptTag = "Basics"
                        )
                    )
                ),
                Lesson(
                    id = "u1_l2",
                    unitId = 1,
                    title = "Document Structure",
                    subtitle = "<!DOCTYPE>, <html>, <head> & <body>",
                    iconName = "layers",
                    exercises = listOf(
                        Exercise(
                            id = "u1_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "The Golden HTML Skeleton",
                            explanation = "Every standard HTML5 document begins with <!DOCTYPE html>. This tells the browser to use modern HTML5 standard mode. Inside <html>, there are two children:\n• <head>: Stores metadata, title, and stylesheets (invisible).\n• <body>: Contains all visible elements (headings, text, images).",
                            codeSnippet = "<!DOCTYPE html>\n<html>\n  <head>\n    <title>My Page</title>\n  </head>\n  <body>\n    <h1>Hello Web!</h1>\n  </body>\n</html>",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l2_e2",
                            type = ExerciseType.CODE_ORDERING,
                            prompt = "Arrange these HTML skeleton elements in proper outer-to-inner order:",
                            correctOrder = listOf("<!DOCTYPE html>", "<html>", "<head>", "<body>"),
                            solutionExplanation = "A document starts with <!DOCTYPE html>, followed by <html>, with <head> first, followed by <body>.",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l2_e3",
                            type = ExerciseType.FIND_MISTAKE,
                            prompt = "Identify the mistake in this document head:",
                            codeSnippet = "<head>\n  <h1>My Main Header</h1>\n  <p>Some text</p>\n</head>",
                            options = listOf(
                                "Visible content (h1, p) belongs in <body>, not <head>",
                                "The <head> tag is misspelled",
                                "Missing CSS styles",
                                "HTML requires an <h1> inside <head>"
                            ),
                            correctAnswer = "Visible content (h1, p) belongs in <body>, not <head>",
                            solutionExplanation = "The <head> tag is meant for page metadata (like <title>, <meta>), while visible content like <h1> and <p> belongs in the <body>!",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l2_e4",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Create a top-level heading <h1> with the text 'HTML Quest'.",
                            starterCode = "<!-- Write your <h1> tag below -->\n",
                            expectedTag = "h1",
                            evaluationRules = listOf("h1", "HTML Quest"),
                            solutionExplanation = "Write <h1>HTML Quest</h1> to create the main heading!",
                            conceptTag = "Basics"
                        )
                    )
                ),
                Lesson(
                    id = "u1_l3",
                    unitId = 1,
                    title = "Your First Webpage",
                    subtitle = "Putting it all together",
                    iconName = "public",
                    exercises = listOf(
                        Exercise(
                            id = "u1_l3_e1",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Where should the user-visible title of a tab be placed?",
                            options = listOf(
                                "Inside the <title> tag inside <head>",
                                "Inside the <h1> tag inside <body>",
                                "Inside the <!DOCTYPE html> declaration",
                                "Directly inside <html> without a parent tag"
                            ),
                            correctAnswer = "Inside the <title> tag inside <head>",
                            solutionExplanation = "The browser tab text is defined by <title>...</title> within the <head> element.",
                            conceptTag = "Basics"
                        ),
                        Exercise(
                            id = "u1_l3_e2",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Build a minimal webpage body with an <h1> and a <p> saying 'Ready to code'.",
                            starterCode = "<body>\n  <h1>Welcome</h1>\n  <!-- Add your paragraph here -->\n</body>",
                            expectedTag = "p",
                            evaluationRules = listOf("h1", "p", "Ready to code"),
                            solutionExplanation = "Add <p>Ready to code</p> inside the <body> tag!",
                            conceptTag = "Basics"
                        )
                    )
                )
            )
        ),

        // UNIT 2: Text
        LearningUnit(
            id = 2,
            title = "Unit 2: Text & Content",
            subtitle = "Headings, Paragraphs & Lists",
            description = "Master headings h1 through h6, paragraphs, line breaks, bold and italic text, and ordered/unordered lists.",
            iconName = "text_fields",
            requiredUnitToUnlock = 1,
            lessons = listOf(
                Lesson(
                    id = "u2_l1",
                    unitId = 2,
                    title = "Headings & Paragraphs",
                    subtitle = "Hierarchy and typography",
                    iconName = "title",
                    exercises = listOf(
                        Exercise(
                            id = "u2_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Heading Hierarchy: h1 to h6",
                            explanation = "HTML has six levels of headings:\n• <h1> is the most important (main title, use one per page for SEO).\n• <h2> to <h6> represent sub-sections in descending order of hierarchy.\n• <p> represents regular paragraphs of text.",
                            codeSnippet = "<h1>Main Title</h1>\n<h2>Chapter 1</h2>\n<h3>Section 1.1</h3>\n<p>Story paragraph goes here.</p>",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which tag creates the largest standard HTML heading?",
                            options = listOf("<p>", "<h1>", "<heading>", "<h6>"),
                            correctAnswer = "<h1>",
                            solutionExplanation = "<h1> is the highest level and largest standard heading, while <h6> is the smallest.",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l1_e3",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Complete this sub-heading tag:",
                            blanksPrefix = "<h2>Chapter One",
                            blanksSuffix = "",
                            options = listOf("</h2>", "</h1>", "</header>", "</heading>"),
                            correctAnswer = "</h2>",
                            solutionExplanation = "The closing tag must match the opening tag: <h2> pairs with </h2>.",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l1_e4",
                            type = ExerciseType.FIND_MISTAKE,
                            prompt = "Find the mistake in this code:",
                            codeSnippet = "<h3>My Subtitle</h1>",
                            options = listOf(
                                "Mismatched opening <h3> and closing </h1>",
                                "Headings cannot contain text",
                                "Missing exclamation mark",
                                "No mistake, this is valid"
                            ),
                            correctAnswer = "Mismatched opening <h3> and closing </h1>",
                            solutionExplanation = "Opening and closing tags must match! An <h3> must be closed with </h3>.",
                            conceptTag = "Text"
                        )
                    )
                ),
                Lesson(
                    id = "u2_l2",
                    unitId = 2,
                    title = "Formatting & Line Breaks",
                    subtitle = "strong, em, br & hr",
                    iconName = "format_bold",
                    exercises = listOf(
                        Exercise(
                            id = "u2_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Inline Formatting Elements",
                            explanation = "• <strong>: Bold text with semantic strong importance.\n• <em>: Italicized text with semantic stress emphasis.\n• <br>: Self-closing line break without creating a new paragraph.\n• <hr>: Horizontal rule / thematic break between paragraphs.",
                            codeSnippet = "<p>This is <strong>critical</strong> and <em>exciting</em>!<br>Next line starts right here.<hr></p>",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l2_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which tag inserts a line break without starting a new paragraph?",
                            options = listOf("<br>", "<lb>", "<break>", "<newline>"),
                            correctAnswer = "<br>",
                            solutionExplanation = "<br> produces a single line break in text (it is a void/self-closing element).",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l2_e3",
                            type = ExerciseType.OUTPUT_PREDICTION,
                            prompt = "What will this render in the browser?",
                            codeSnippet = "<p>Learn <strong>HTML</strong> today.</p>",
                            options = listOf(
                                "Learn HTML today. (with HTML in bold)",
                                "Learn HTML today. (with HTML in italics)",
                                "Learn <strong HTML strong> today.",
                                "A list containing Learn and HTML"
                            ),
                            correctAnswer = "Learn HTML today. (with HTML in bold)",
                            solutionExplanation = "<strong> makes the enclosed text bold and marks it with strong importance.",
                            conceptTag = "Text"
                        )
                    )
                ),
                Lesson(
                    id = "u2_l3",
                    unitId = 2,
                    title = "Ordered & Unordered Lists",
                    subtitle = "ul, ol & li",
                    iconName = "format_list_bulleted",
                    exercises = listOf(
                        Exercise(
                            id = "u2_l3_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "List Elements",
                            explanation = "HTML offers two main list types:\n• <ul>: Unordered list (bullet points)\n• <ol>: Ordered list (numbered 1, 2, 3...)\n• <li>: List item, used inside both <ul> and <ol>.",
                            codeSnippet = "<ul>\n  <li>Apples</li>\n  <li>Bananas</li>\n</ul>\n<ol>\n  <li>Step one</li>\n  <li>Step two</li>\n</ol>",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l3_e2",
                            type = ExerciseType.CODE_ORDERING,
                            prompt = "Order the elements to create a valid numbered list:",
                            correctOrder = listOf("<ol>", "<li>First</li>", "<li>Second</li>", "</ol>"),
                            solutionExplanation = "An ordered list starts with <ol>, contains <li> items, and closes with </ol>.",
                            conceptTag = "Text"
                        ),
                        Exercise(
                            id = "u2_l3_e3",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Create an unordered list (ul) with items 'HTML' and 'CSS'.",
                            starterCode = "<!-- Write your unordered list below -->\n",
                            expectedTag = "ul",
                            evaluationRules = listOf("ul", "li", "HTML", "CSS"),
                            solutionExplanation = "Use <ul><li>HTML</li><li>CSS</li></ul> to build the list!",
                            conceptTag = "Text"
                        )
                    )
                )
            )
        ),

        // UNIT 3: Links & Images
        LearningUnit(
            id = 3,
            title = "Unit 3: Links & Images",
            subtitle = "Hyperlinks and Visual Media",
            description = "Connect the web with anchor tags (href) and display pictures with <img> and accessibility alt attributes.",
            iconName = "link",
            requiredUnitToUnlock = 2,
            lessons = listOf(
                Lesson(
                    id = "u3_l1",
                    unitId = 3,
                    title = "The Anchor Tag & Href",
                    subtitle = "Hyperlinks to anywhere",
                    iconName = "open_in_browser",
                    exercises = listOf(
                        Exercise(
                            id = "u3_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Creating Hyperlinks with <a>",
                            explanation = "The <a> (anchor) tag creates links to other pages or resources. Its most important attribute is 'href' (hypertext reference), which specifies the destination URL.",
                            codeSnippet = "<a href=\"https://developer.mozilla.org\">Visit MDN</a>",
                            conceptTag = "Links"
                        ),
                        Exercise(
                            id = "u3_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which attribute defines the destination URL in an <a> tag?",
                            options = listOf("href", "src", "link", "url"),
                            correctAnswer = "href",
                            solutionExplanation = "'href' stands for hypertext reference and points to the link's destination.",
                            conceptTag = "Links"
                        ),
                        Exercise(
                            id = "u3_l1_e3",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Fill in the attribute name to open link in a new tab:",
                            blanksPrefix = "<a href=\"https://google.com\" ",
                            blanksSuffix = "=\"_blank\">Google</a>",
                            options = listOf("target", "window", "tab", "open"),
                            correctAnswer = "target",
                            solutionExplanation = "target=\"_blank\" instructs the browser to open the link in a new tab or window.",
                            conceptTag = "Links"
                        )
                    )
                ),
                Lesson(
                    id = "u3_l2",
                    unitId = 3,
                    title = "Images & The Alt Attribute",
                    subtitle = "src, alt & image sizing",
                    iconName = "image",
                    exercises = listOf(
                        Exercise(
                            id = "u3_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Embedding Images with <img>",
                            explanation = "The <img> tag is self-closing (void). It requires:\n• src: Source file path or web URL of the image.\n• alt: Alternative text description, crucial for screen readers and when images fail to load.",
                            codeSnippet = "<img src=\"logo.png\" alt=\"HTML Quest App Logo\" width=\"120\">",
                            conceptTag = "Images"
                        ),
                        Exercise(
                            id = "u3_l2_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Why is the 'alt' attribute mandatory on <img> tags?",
                            options = listOf(
                                "It provides text for screen readers and SEO when the image cannot be shown",
                                "It makes the image load faster",
                                "It changes the image color filter",
                                "It allows JavaScript to rotate the image"
                            ),
                            correctAnswer = "It provides text for screen readers and SEO when the image cannot be shown",
                            solutionExplanation = "alt text makes websites accessible for visually impaired learners using screen readers, and displays if an image fails to load.",
                            conceptTag = "Images"
                        ),
                        Exercise(
                            id = "u3_l2_e3",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Write an <img> tag with src='quest.png' and alt='Quest shield'.",
                            starterCode = "<!-- Write your img tag below -->\n",
                            expectedTag = "img",
                            evaluationRules = listOf("img", "src", "quest.png", "alt"),
                            solutionExplanation = "Write <img src=\"quest.png\" alt=\"Quest shield\"> to complete the challenge!",
                            conceptTag = "Images"
                        )
                    )
                )
            )
        ),

        // UNIT 4: Semantic HTML
        LearningUnit(
            id = 4,
            title = "Unit 4: Semantic HTML",
            subtitle = "Meaningful Page Structure",
            description = "Replace endless generic <div> tags with semantic markup: <header>, <nav>, <main>, <section>, <article>, and <footer>.",
            iconName = "account_tree",
            requiredUnitToUnlock = 3,
            lessons = listOf(
                Lesson(
                    id = "u4_l1",
                    unitId = 4,
                    title = "Page Layout Semantics",
                    subtitle = "header, nav, main, footer",
                    iconName = "view_quilt",
                    exercises = listOf(
                        Exercise(
                            id = "u4_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Why Semantics Matter",
                            explanation = "A semantic element clearly describes its meaning to both the browser and developer. Instead of using <div> for everything:\n• <header>: Introductory content or branding\n• <nav>: Navigation links\n• <main>: The primary content unique to this page\n• <footer>: Copyright, contact, and legal links",
                            codeSnippet = "<header>\n  <nav><a href=\"#\">Home</a></nav>\n</header>\n<main>\n  <h1>Welcome</h1>\n</main>\n<footer>&copy; 2026 HTML Quest</footer>",
                            conceptTag = "Semantic HTML"
                        ),
                        Exercise(
                            id = "u4_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which element should wrap the primary unique content of a document?",
                            options = listOf("<main>", "<primary>", "<content>", "<center>"),
                            correctAnswer = "<main>",
                            solutionExplanation = "<main> represents the dominant, unique content of the body. There should only be one non-hidden <main> per document.",
                            conceptTag = "Semantic HTML"
                        ),
                        Exercise(
                            id = "u4_l1_e3",
                            type = ExerciseType.CODE_ORDERING,
                            prompt = "Arrange standard semantic layout sections from top to bottom:",
                            correctOrder = listOf("<header>", "<nav>", "<main>", "<footer>"),
                            solutionExplanation = "Standard webpage flow usually has header & navigation at top, main in the middle, and footer at bottom.",
                            conceptTag = "Semantic HTML"
                        )
                    )
                ),
                Lesson(
                    id = "u4_l2",
                    unitId = 4,
                    title = "Sectioning & Aside",
                    subtitle = "section, article & aside",
                    iconName = "chrome_reader_mode",
                    exercises = listOf(
                        Exercise(
                            id = "u4_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Articles vs Sections",
                            explanation = "• <article>: Self-contained composition that makes sense on its own (blog post, news item, forum comment).\n• <section>: A standalone thematic grouping of content, typically with a heading.\n• <aside>: Indirectly related content like sidebars, callouts, or author bios.",
                            codeSnippet = "<article>\n  <h2>Latest News</h2>\n  <p>Article body...</p>\n</article>\n<aside>\n  <h3>Related Links</h3>\n</aside>",
                            conceptTag = "Semantic HTML"
                        ),
                        Exercise(
                            id = "u4_l2_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which tag is best suited for an independent blog post that could be syndicated?",
                            options = listOf("<article>", "<div>", "<aside>", "<span>"),
                            correctAnswer = "<article>",
                            solutionExplanation = "<article> signifies self-contained content that can be distributed or reused independently.",
                            conceptTag = "Semantic HTML"
                        )
                    )
                )
            )
        ),

        // UNIT 5: Tables
        LearningUnit(
            id = 5,
            title = "Unit 5: Tables",
            subtitle = "Tabular Data & Grids",
            description = "Format spreadsheets and data matrices with <table>, <tr>, <th>, <td>, <thead>, and <tbody>.",
            iconName = "table_chart",
            requiredUnitToUnlock = 4,
            lessons = listOf(
                Lesson(
                    id = "u5_l1",
                    unitId = 5,
                    title = "Rows, Headers & Data Cells",
                    subtitle = "table, tr, th, td",
                    iconName = "grid_on",
                    exercises = listOf(
                        Exercise(
                            id = "u5_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Table Anatomy",
                            explanation = "• <table>: Container for the table.\n• <tr>: Table row (horizontal line).\n• <th>: Table header cell (bold, centered by default).\n• <td>: Table data cell (regular content).",
                            codeSnippet = "<table border=\"1\">\n  <tr>\n    <th>Name</th>\n    <th>Score</th>\n  </tr>\n  <tr>\n    <td>Alex</td>\n    <td>95</td>\n  </tr>\n</table>",
                            conceptTag = "Tables"
                        ),
                        Exercise(
                            id = "u5_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which tag represents a single cell containing standard data in a table row?",
                            options = listOf("<td>", "<th>", "<cell>", "<data>"),
                            correctAnswer = "<td>",
                            solutionExplanation = "<td> stands for 'table data' and represents an individual data cell in a row.",
                            conceptTag = "Tables"
                        ),
                        Exercise(
                            id = "u5_l1_e3",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Create a table with one row (tr) containing header cells (th) for 'City' and 'Country'.",
                            starterCode = "<table>\n  <!-- Create your header row here -->\n</table>",
                            expectedTag = "table",
                            evaluationRules = listOf("table", "tr", "th", "City", "Country"),
                            solutionExplanation = "Add <tr><th>City</th><th>Country</th></tr> inside <table>!",
                            conceptTag = "Tables"
                        )
                    )
                ),
                Lesson(
                    id = "u5_l2",
                    unitId = 5,
                    title = "Structured Tables",
                    subtitle = "thead, tbody, tfoot",
                    iconName = "view_stream",
                    exercises = listOf(
                        Exercise(
                            id = "u5_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Semantic Table Partitioning",
                            explanation = "For accessible, maintainable tables, group rows into:\n• <thead>: Table header block\n• <tbody>: Main body rows\n• <tfoot>: Footer summary / totals row",
                            codeSnippet = "<table>\n  <thead>\n    <tr><th>Item</th><th>Price</th></tr>\n  </thead>\n  <tbody>\n    <tr><td>Coffee</td><td>$4</td></tr>\n  </tbody>\n  <tfoot>\n    <tr><td>Total</td><td>$4</td></tr>\n  </tfoot>\n</table>",
                            conceptTag = "Tables"
                        ),
                        Exercise(
                            id = "u5_l2_e2",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Complete the closing tag for the table header section:",
                            blanksPrefix = "<thead>\n  <tr><th>Score</th></tr>\n",
                            blanksSuffix = "",
                            options = listOf("</thead>", "</tfoot>", "</tr>", "</table>"),
                            correctAnswer = "</thead>",
                            solutionExplanation = "The <thead> block is closed with </thead>.",
                            conceptTag = "Tables"
                        )
                    )
                )
            )
        ),

        // UNIT 6: Forms
        LearningUnit(
            id = 6,
            title = "Unit 6: Forms & User Input",
            subtitle = "Inputs, Labels, Buttons & Controls",
            description = "Build interactive forms that collect user data with inputs, labels, selects, checkboxes, and buttons.",
            iconName = "dynamic_form",
            requiredUnitToUnlock = 5,
            lessons = listOf(
                Lesson(
                    id = "u6_l1",
                    unitId = 6,
                    title = "Forms & Text Inputs",
                    subtitle = "form, input, label",
                    iconName = "input",
                    exercises = listOf(
                        Exercise(
                            id = "u6_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Building Forms",
                            explanation = "Forms gather user input and submit it to a server:\n• <form>: Enclosing container with 'action' and 'method' attributes.\n• <label>: Accessible label linked via 'for' attribute to input's 'id'.\n• <input type=\"text\">: Single-line text input field.",
                            codeSnippet = "<form action=\"/submit\" method=\"POST\">\n  <label for=\"username\">Username:</label>\n  <input type=\"text\" id=\"username\" name=\"user\">\n  <button type=\"submit\">Submit</button>\n</form>",
                            conceptTag = "Forms"
                        ),
                        Exercise(
                            id = "u6_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which input type masks user keystrokes for secure password entry?",
                            options = listOf("type=\"password\"", "type=\"secret\"", "type=\"hidden\"", "type=\"secure\""),
                            correctAnswer = "type=\"password\"",
                            solutionExplanation = "<input type=\"password\"> masks the characters entered by the user with dots or asterisks.",
                            conceptTag = "Forms"
                        ),
                        Exercise(
                            id = "u6_l1_e3",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Create an email input with type='email' and placeholder='Enter email'.",
                            starterCode = "<!-- Write your email input below -->\n",
                            expectedTag = "input",
                            evaluationRules = listOf("input", "email", "placeholder"),
                            solutionExplanation = "Write <input type=\"email\" placeholder=\"Enter email\"> to complete the challenge!",
                            conceptTag = "Forms"
                        )
                    )
                ),
                Lesson(
                    id = "u6_l2",
                    unitId = 6,
                    title = "Checkboxes, Radios & Selects",
                    subtitle = "Multi-choice form controls",
                    iconName = "check_box",
                    exercises = listOf(
                        Exercise(
                            id = "u6_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Choice Controls",
                            explanation = "• Checkboxes (<input type=\"checkbox\">): Allow selecting multiple choices.\n• Radio buttons (<input type=\"radio\" name=\"group\">): Allow selecting only ONE option from a group with the same 'name'.\n• <select> & <option>: Dropdown menu.",
                            codeSnippet = "<select name=\"fruit\">\n  <option value=\"apple\">Apple</option>\n  <option value=\"orange\">Orange</option>\n</select>",
                            conceptTag = "Forms"
                        ),
                        Exercise(
                            id = "u6_l2_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "How do you ensure only ONE radio button in a set can be chosen at a time?",
                            options = listOf(
                                "Give them all the same 'name' attribute value",
                                "Give them the same 'id' attribute",
                                "Wrap them in a <div>",
                                "Use type=\"single-radio\""
                            ),
                            correctAnswer = "Give them all the same 'name' attribute value",
                            solutionExplanation = "Radio buttons share mutual exclusivity when their 'name' attribute matches!",
                            conceptTag = "Forms"
                        ),
                        Exercise(
                            id = "u6_l2_e3",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Complete this multi-line text input tag:",
                            blanksPrefix = "<textarea rows=\"4\" cols=\"50\">Your bio",
                            blanksSuffix = "",
                            options = listOf("</textarea>", "</input>", "</text>", "</box>"),
                            correctAnswer = "</textarea>",
                            solutionExplanation = "<textarea> requires a closing </textarea> tag and can hold multiline text.",
                            conceptTag = "Forms"
                        )
                    )
                )
            )
        ),

        // UNIT 7: Multimedia
        LearningUnit(
            id = 7,
            title = "Unit 7: Multimedia",
            subtitle = "Audio, Video & Iframes",
            description = "Embed rich native sounds, video clips, and external frames with <audio>, <video>, and <iframe>.",
            iconName = "movie",
            requiredUnitToUnlock = 6,
            lessons = listOf(
                Lesson(
                    id = "u7_l1",
                    unitId = 7,
                    title = "Audio & Video Elements",
                    subtitle = "Native playback in HTML5",
                    iconName = "play_circle",
                    exercises = listOf(
                        Exercise(
                            id = "u7_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Native Audio and Video",
                            explanation = "HTML5 brought native media playback without plugins:\n• <video src=\"clip.mp4\" controls>: Displays player with play/pause/volume.\n• <audio src=\"track.mp3\" controls>: Audio player.\n• <source>: Allows specifying multiple fallback formats (mp4, webm, ogg).",
                            codeSnippet = "<video controls width=\"320\">\n  <source src=\"movie.mp4\" type=\"video/mp4\">\n  Your browser does not support video.\n</video>",
                            conceptTag = "Multimedia"
                        ),
                        Exercise(
                            id = "u7_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which attribute adds play, pause, and volume buttons to a <video> element?",
                            options = listOf("controls", "buttons", "player", "playback"),
                            correctAnswer = "controls",
                            solutionExplanation = "The boolean attribute 'controls' enables the browser's built-in playback interface.",
                            conceptTag = "Multimedia"
                        )
                    )
                ),
                Lesson(
                    id = "u7_l2",
                    unitId = 7,
                    title = "Inline Frames (iframe)",
                    subtitle = "Embedding external content",
                    iconName = "web",
                    exercises = listOf(
                        Exercise(
                            id = "u7_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "The <iframe> Element",
                            explanation = "An <iframe> embeds another HTML document inside the current page (e.g. YouTube videos, maps, widgets). You specify 'src' for the page URL and 'title' for accessibility.",
                            codeSnippet = "<iframe src=\"https://example.com\" title=\"Example Page\" width=\"400\" height=\"300\"></iframe>",
                            conceptTag = "Multimedia"
                        ),
                        Exercise(
                            id = "u7_l2_e2",
                            type = ExerciseType.FILL_BLANK,
                            prompt = "Fill in the attribute specifying the frame's destination:",
                            blanksPrefix = "<iframe ",
                            blanksSuffix = "=\"https://wikipedia.org\" title=\"Wikipedia\"></iframe>",
                            options = listOf("src", "href", "url", "frame"),
                            correctAnswer = "src",
                            solutionExplanation = "Just like <img>, <iframe> uses 'src' to indicate the content source URL.",
                            conceptTag = "Multimedia"
                        )
                    )
                )
            )
        ),

        // UNIT 8: HTML Attributes
        LearningUnit(
            id = 8,
            title = "Unit 8: HTML Attributes",
            subtitle = "id, class, style & data-*",
            description = "Understand global attributes, targeting elements with id and class, inline styling, and custom data attributes.",
            iconName = "tune",
            requiredUnitToUnlock = 7,
            lessons = listOf(
                Lesson(
                    id = "u8_l1",
                    unitId = 8,
                    title = "id vs class",
                    subtitle = "Identifiers and classes",
                    iconName = "fingerprint",
                    exercises = listOf(
                        Exercise(
                            id = "u8_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Unique IDs vs Reusable Classes",
                            explanation = "• id: Must be UNIQUE within the entire document. Used for anchor links, JavaScript, and specific styles.\n• class: Can be reused across MULTIPLE elements. An element can also have multiple classes separated by spaces.",
                            codeSnippet = "<div id=\"main-hero\" class=\"banner glow\">\n  <h1 class=\"title\">Welcome</h1>\n</div>",
                            conceptTag = "Attributes"
                        ),
                        Exercise(
                            id = "u8_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "Which attribute MUST have a unique value per HTML document?",
                            options = listOf("id", "class", "style", "title"),
                            correctAnswer = "id",
                            solutionExplanation = "The 'id' attribute must be unique across the entire HTML document.",
                            conceptTag = "Attributes"
                        ),
                        Exercise(
                            id = "u8_l1_e3",
                            type = ExerciseType.CODE_CHALLENGE,
                            prompt = "Create a paragraph <p> with class='highlight' and id='intro'.",
                            starterCode = "<!-- Write your paragraph below -->\n",
                            expectedTag = "p",
                            evaluationRules = listOf("p", "class", "highlight", "id", "intro"),
                            solutionExplanation = "Write <p class=\"highlight\" id=\"intro\">Intro text</p> to satisfy both attributes!",
                            conceptTag = "Attributes"
                        )
                    )
                ),
                Lesson(
                    id = "u8_l2",
                    unitId = 8,
                    title = "Custom Data Attributes",
                    subtitle = "data-* and global attributes",
                    iconName = "label",
                    exercises = listOf(
                        Exercise(
                            id = "u8_l2_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "The data-* Attribute",
                            explanation = "HTML5 lets developers store custom data directly on elements with attributes starting with 'data-'. They don't affect styling directly but can be read by JavaScript.",
                            codeSnippet = "<button data-user-id=\"42\" data-role=\"admin\">Manage User</button>",
                            conceptTag = "Attributes"
                        ),
                        Exercise(
                            id = "u8_l2_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "What prefix is used for valid custom HTML5 data attributes?",
                            options = listOf("data-", "custom-", "var-", "prop-"),
                            correctAnswer = "data-",
                            solutionExplanation = "Attributes starting with 'data-' are reserved for private author data in HTML5.",
                            conceptTag = "Attributes"
                        )
                    )
                )
            )
        ),

        // UNIT 9: Accessibility
        LearningUnit(
            id = 9,
            title = "Unit 9: Accessibility (a11y)",
            subtitle = "Building for Every Human",
            description = "Make webpages accessible using semantic tags, descriptive alt text, form labels, and introductory ARIA roles.",
            iconName = "accessibility_new",
            requiredUnitToUnlock = 8,
            lessons = listOf(
                Lesson(
                    id = "u9_l1",
                    unitId = 9,
                    title = "Accessible HTML",
                    subtitle = "Semantic markup & alt text",
                    iconName = "visibility",
                    exercises = listOf(
                        Exercise(
                            id = "u9_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Web Accessibility Matters",
                            explanation = "Over 1 billion people live with disabilities. Accessible HTML ensures everyone can use your site with screen readers or keyboards:\n• Use semantic elements (<button> instead of <div onclick>).\n• Always provide descriptive 'alt' on images.\n• Always connect <label for=\"id\"> with <input id=\"id\">.",
                            codeSnippet = "<button type=\"button\">Save Draft</button>\n<img src=\"chart.png\" alt=\"Quarterly revenue growth showing 25% increase\">",
                            conceptTag = "Accessibility"
                        ),
                        Exercise(
                            id = "u9_l1_e2",
                            type = ExerciseType.FIND_MISTAKE,
                            prompt = "Identify the accessibility flaw in this form control:",
                            codeSnippet = "<span onclick=\"submit()\">Submit</span>",
                            options = listOf(
                                "Using a <span> instead of a real <button> breaks keyboard navigation and screen readers",
                                "The text should say 'Click'",
                                "Missing CSS class",
                                "No flaw exists"
                            ),
                            correctAnswer = "Using a <span> instead of a real <button> breaks keyboard navigation and screen readers",
                            solutionExplanation = "Always use real native <button> elements! They receive keyboard focus and announce their role to screen readers.",
                            conceptTag = "Accessibility"
                        ),
                        Exercise(
                            id = "u9_l1_e3",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "What attribute connects a <label> explicitly to its target <input>?",
                            options = listOf("for", "target", "id", "input"),
                            correctAnswer = "for",
                            solutionExplanation = "The <label for=\"inputId\"> matches the <input id=\"inputId\">, enabling clicks on the label to focus the input.",
                            conceptTag = "Accessibility"
                        )
                    )
                )
            )
        ),

        // UNIT 10: Real Projects
        LearningUnit(
            id = 10,
            title = "Unit 10: Real Projects",
            subtitle = "From Concept to Website",
            description = "Apply your knowledge by building 7 real-world projects: Profile, Recipe, Contact Form, Blog, Product, Portfolio, and Final Website!",
            iconName = "rocket_launch",
            requiredUnitToUnlock = 9,
            lessons = listOf(
                Lesson(
                    id = "u10_l1",
                    unitId = 10,
                    title = "Project Studio Introduction",
                    subtitle = "Your journey to HTML Mastery",
                    iconName = "military_tech",
                    exercises = listOf(
                        Exercise(
                            id = "u10_l1_e1",
                            type = ExerciseType.EXPLANATION,
                            prompt = "Welcome to the Project Studio!",
                            explanation = "Congratulations on reaching Unit 10! You've learned all the core HTML tags, semantics, forms, tables, and accessibility standards. Now, head over to the Projects tab to complete real-world projects and earn the HTML Complete badge!",
                            codeSnippet = "<!-- You are now an HTML Architect! -->\n<div class=\"architect-badge\">\n  <h1>Project Ready</h1>\n</div>",
                            conceptTag = "Projects"
                        ),
                        Exercise(
                            id = "u10_l1_e2",
                            type = ExerciseType.MULTIPLE_CHOICE,
                            prompt = "What is the best way to solidify your coding skills?",
                            options = listOf(
                                "Building real projects and practicing regularly",
                                "Memorizing tags without writing them",
                                "Only reading documentation",
                                "Copy-pasting code without understanding"
                            ),
                            correctAnswer = "Building real projects and practicing regularly",
                            solutionExplanation = "Building real projects gives you hands-on experience and cements practical concepts!",
                            conceptTag = "Projects"
                        )
                    )
                )
            )
        )
    )

    fun getLessonById(lessonId: String): Lesson? {
        return units.flatMap { it.lessons }.find { it.id == lessonId }
    }

    fun getUnitById(unitId: Int): LearningUnit? {
        return units.find { it.id == unitId }
    }
}
