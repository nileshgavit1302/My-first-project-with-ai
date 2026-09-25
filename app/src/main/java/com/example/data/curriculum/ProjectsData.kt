package com.example.data.curriculum

import com.example.data.model.HtmlProject

object ProjectsData {

    val allProjects: List<HtmlProject> = listOf(
        HtmlProject(
            id = "proj_profile",
            title = "Personal Profile Page",
            subtitle = "Introduce yourself to the web",
            difficulty = "Beginner",
            iconEmoji = "👤",
            description = "Build a personal developer bio page with a main heading, introductory bio paragraph, profile image or avatar placeholder, and a list of your top skills.",
            requirements = listOf(
                "Use an <h1> heading for your name or title",
                "Include a <p> paragraph introducing yourself",
                "Include an <img> tag with src and alt attributes",
                "Create an unordered list <ul> of your favorite skills"
            ),
            requiredTags = listOf("h1", "p", "img", "ul"),
            starterCode = """<!DOCTYPE html>
<html>
<head>
  <title>My Profile</title>
</head>
<body>
  <!-- Step 1: Add your <h1> name -->
  <h1>Alex Rivers - Frontend Apprentice</h1>

  <!-- Step 2: Add your intro paragraph -->
  <p>Hello world! I am embarking on my journey to master HTML and web development with HTML Quest.</p>

  <!-- Step 3: Add your avatar image -->
  <img src="https://picsum.photos/120/120" alt="Alex Rivers Profile Photo" style="border-radius:50%;">

  <!-- Step 4: Add your skills list -->
  <h3>Skills</h3>
  <ul>
    <li>HTML5 Semantics</li>
    <li>Responsive Layouts</li>
    <li>Clean Markup</li>
  </ul>
</body>
</html>""",
            solutionCode = """<!DOCTYPE html>
<html>
<head>
  <title>Alex Profile</title>
</head>
<body>
  <h1>Alex Rivers</h1>
  <p>Passionate developer learning HTML and modern web standards.</p>
  <img src="https://picsum.photos/120" alt="Avatar">
  <ul>
    <li>HTML5</li>
    <li>Accessibility</li>
  </ul>
</body>
</html>""",
            xpReward = 150
        ),

        HtmlProject(
            id = "proj_recipe",
            title = "Delicious Recipe Card",
            subtitle = "Ingredients, steps & cooking notes",
            difficulty = "Beginner",
            iconEmoji = "🍳",
            description = "Create a structured recipe webpage featuring a header, description, unordered list of ingredients, and an ordered list for preparation instructions.",
            requirements = listOf(
                "Use an <h1> for the recipe title",
                "Include an <h2> subhead for Ingredients",
                "Include an <ul> list for ingredients",
                "Include an <ol> list for step-by-step instructions"
            ),
            requiredTags = listOf("h1", "h2", "ul", "ol"),
            starterCode = """<!DOCTYPE html>
<html>
<head>
  <title>Classic Pancakes</title>
</head>
<body>
  <h1>Golden Fluffy Pancakes</h1>
  <p>The ultimate weekend breakfast treat, light, fluffy, and golden.</p>

  <h2>Ingredients</h2>
  <ul>
    <li>1 cup all-purpose flour</li>
    <li>2 tablespoons sugar</li>
    <li>1 cup fresh milk</li>
    <li>1 fresh egg</li>
  </ul>

  <h2>Instructions</h2>
  <ol>
    <li>Whisk dry ingredients together in a large bowl.</li>
    <li>Pour in milk, egg, and melted butter. Whisk until smooth.</li>
    <li>Cook on a hot griddle until bubbles form, then flip!</li>
  </ol>
</body>
</html>""",
            solutionCode = """<!DOCTYPE html>
<html>
<body>
  <h1>Recipe</h1>
  <h2>Ingredients</h2>
  <ul><li>Flour</li></ul>
  <ol><li>Cook</li></ol>
</body>
</html>""",
            xpReward = 150
        ),

        HtmlProject(
            id = "proj_contact",
            title = "Interactive Contact Form",
            subtitle = "Collect messages and feedback",
            difficulty = "Intermediate",
            iconEmoji = "📬",
            description = "Construct an accessible contact form with name, email, topic selector, message textarea, and a submit button.",
            requirements = listOf(
                "Wrap inputs in a <form> container",
                "Provide <label> elements for accessibility",
                "Include text/email <input> fields",
                "Include a <textarea> for the message",
                "Include a <button type='submit'> button"
            ),
            requiredTags = listOf("form", "label", "input", "textarea", "button"),
            starterCode = """<!DOCTYPE html>
<html>
<head>
  <title>Contact Us</title>
</head>
<body>
  <h2>Get in Touch</h2>
  <form action="#" method="POST">
    <div>
      <label for="name">Your Name:</label>
      <input type="text" id="name" name="name" required placeholder="Jane Doe">
    </div>
    <br>
    <div>
      <label for="email">Email Address:</label>
      <input type="email" id="email" name="email" required placeholder="jane@example.com">
    </div>
    <br>
    <div>
      <label for="msg">Message:</label>
      <textarea id="msg" name="message" rows="4" placeholder="Your thoughts..."></textarea>
    </div>
    <br>
    <button type="submit">Send Message</button>
  </form>
</body>
</html>""",
            solutionCode = """<form>
  <label for="n">Name</label><input id="n">
  <textarea></textarea>
  <button type="submit">Send</button>
</form>""",
            xpReward = 150
        ),

        HtmlProject(
            id = "proj_blog",
            title = "Semantic Blog Article",
            subtitle = "Rich articles with semantic markup",
            difficulty = "Intermediate",
            iconEmoji = "📰",
            description = "Build a clean blog post layout using semantic tags: <header>, <article>, <section>, <aside>, and <footer>.",
            requirements = listOf(
                "Use an <article> container for the post",
                "Include a <header> with title and post metadata",
                "Include <section> blocks for article chapters",
                "Include an <aside> for author bio or related links",
                "Include a <footer> with copyright info"
            ),
            requiredTags = listOf("article", "header", "section", "aside", "footer"),
            starterCode = """<!DOCTYPE html>
<html>
<body>
  <article>
    <header>
      <h1>Why the Modern Web Needs HTML5</h1>
      <p>Published on September 25 by <em>Code Explorer</em></p>
    </header>

    <section>
      <h2>The Power of Semantics</h2>
      <p>Semantic tags make web pages understandable to both machines and humans alike.</p>
    </section>

    <aside>
      <h3>About the Author</h3>
      <p>Passionate coder building developer tools and open educational games.</p>
    </aside>

    <footer>
      <p>&copy; 2026 Developer Digest. All rights reserved.</p>
    </footer>
  </article>
</body>
</html>""",
            solutionCode = """<article>
  <header><h1>Blog</h1></header>
  <section><p>Body</p></section>
  <aside><p>Bio</p></aside>
  <footer><p>End</p></footer>
</article>""",
            xpReward = 150
        ),

        HtmlProject(
            id = "proj_product",
            title = "E-Commerce Product Showcase",
            subtitle = "Display item details, specs & pricing table",
            difficulty = "Intermediate",
            iconEmoji = "🛍️",
            description = "Design a commercial product detail page featuring a product picture, description, pricing table, customer reviews, and an 'Add to Cart' button.",
            requirements = listOf(
                "Include an <h1> for product name",
                "Include an <img> with product preview",
                "Include a <table> displaying product specifications",
                "Include a <button> for purchasing"
            ),
            requiredTags = listOf("h1", "img", "table", "button"),
            starterCode = """<!DOCTYPE html>
<html>
<body>
  <h1>Ergonomic Mechanical Keyboard</h1>
  <img src="https://picsum.photos/300/180" alt="Mechanical Keyboard on desk">
  <p>Ultra-responsive switches with custom RGB lighting and wireless connectivity.</p>

  <h3>Technical Specifications</h3>
  <table border="1" cellpadding="6">
    <tr>
      <th>Feature</th>
      <th>Specification</th>
    </tr>
    <tr>
      <td>Switches</td>
      <td>Tactile Brown</td>
    </tr>
    <tr>
      <td>Battery Life</td>
      <td>120 Hours</td>
    </tr>
  </table>

  <br>
  <button type="button" style="padding:10px 20px; font-weight:bold;">Add to Cart - $89.00</button>
</body>
</html>""",
            solutionCode = """<h1>Product</h1>
<img src="pic.jpg" alt="Item">
<table><tr><th>Spec</th></tr></table>
<button>Buy</button>""",
            xpReward = 150
        ),

        HtmlProject(
            id = "proj_portfolio",
            title = "Developer Portfolio",
            subtitle = "Showcase your web accomplishments",
            difficulty = "Advanced",
            iconEmoji = "💼",
            description = "Build an attractive portfolio landing page with navigation links, project grid, about section, and contact callout.",
            requirements = listOf(
                "Use a <nav> for site navigation links",
                "Use a <main> tag for primary showcase content",
                "Include internal anchor links with href and id",
                "Provide an accessible <footer> with social links"
            ),
            requiredTags = listOf("nav", "main", "a", "footer"),
            starterCode = """<!DOCTYPE html>
<html>
<head>
  <title>Jordan Dev - Portfolio</title>
</head>
<body>
  <header>
    <h2>Jordan Code</h2>
    <nav>
      <a href="#about">About</a> |
      <a href="#work">Work</a> |
      <a href="#contact">Contact</a>
    </nav>
  </header>

  <main>
    <section id="about">
      <h1>Frontend Architect</h1>
      <p>Crafting accessible, lightweight, modern web experiences.</p>
    </section>

    <section id="work">
      <h2>Featured Projects</h2>
      <ul>
        <li><strong>HTML Quest</strong> - Interactive learning game</li>
        <li><strong>Recipe Box</strong> - Offline cooking catalog</li>
      </ul>
    </section>
  </main>

  <footer id="contact">
    <p>Let's collaborate: <a href="mailto:jordan@dev.com">jordan@dev.com</a></p>
  </footer>
</body>
</html>""",
            solutionCode = """<header><nav><a href="#m">Link</a></nav></header>
<main id="m"><h1>Main</h1></main>
<footer><p>Footer</p></footer>""",
            xpReward = 200
        ),

        HtmlProject(
            id = "proj_final_site",
            title = "Multi-Section Web Portal",
            subtitle = "The Capstone HTML Website",
            difficulty = "Mastery",
            iconEmoji = "🏛️",
            description = "Create a comprehensive multi-section web portal incorporating <!DOCTYPE html>, semantic layouts (<header>, <nav>, <main>, <section>, <article>, <aside>, <footer>), tabular data, media, and interactive forms.",
            requirements = listOf(
                "Include complete <!DOCTYPE html> document structure",
                "Use semantic layout: <header>, <nav>, <main>, <section>, <footer>",
                "Include a <table> with structured headers",
                "Include a <form> with input controls and button"
            ),
            requiredTags = listOf("html", "header", "nav", "main", "table", "form"),
            starterCode = """<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>City Portal</title>
</head>
<body>
  <header>
    <h1>Metro City Explorer</h1>
    <nav>
      <a href="#events">Events</a> |
      <a href="#schedule">Transit</a> |
      <a href="#feedback">Feedback</a>
    </nav>
  </header>

  <main>
    <section id="events">
      <h2>Upcoming Festivals</h2>
      <p>Discover community happenings around the city.</p>
    </section>

    <section id="schedule">
      <h2>Subway Schedule</h2>
      <table border="1" cellpadding="6">
        <thead>
          <tr><th>Line</th><th>Frequency</th></tr>
        </thead>
        <tbody>
          <tr><td>Blue Line</td><td>Every 5 min</td></tr>
          <tr><td>Green Line</td><td>Every 8 min</td></tr>
        </tbody>
      </table>
    </section>

    <section id="feedback">
      <h2>Citizen Inquiries</h2>
      <form action="#" method="POST">
        <label for="inquiry">Message:</label><br>
        <input type="text" id="inquiry" name="inquiry" required placeholder="Your message...">
        <button type="submit">Submit</button>
      </form>
    </section>
  </main>

  <footer>
    <p>&copy; 2026 Metro City Council. Built with Semantic HTML.</p>
  </footer>
</body>
</html>""",
            solutionCode = """<!DOCTYPE html>
<html>
<body>
  <header><nav><a href="#">Link</a></nav></header>
  <main>
    <table><tr><th>Header</th></tr></table>
    <form><input><button>Go</button></form>
  </main>
  <footer><p>End</p></footer>
</body>
</html>""",
            xpReward = 250
        )
    )

    fun getProjectById(id: String): HtmlProject? {
        return allProjects.find { it.id == id }
    }
}
