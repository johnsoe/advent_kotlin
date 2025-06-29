<article class="day-desc"><h2>--- Day 3: No Matter How You Slice It ---</h2><p>The Elves managed to locate the chimney-squeeze prototype fabric for Santa's suit (thanks to <span title="WAS IT YOU">someone</span> who helpfully wrote its box IDs on the wall of the warehouse in the middle of the night).  Unfortunately, anomalies are still affecting them - nobody can even agree on how to <em>cut</em> the fabric.</p>
<p>The whole piece of fabric they're working on is a very large square - at least <code>1000</code> inches on each side.</p>
<p>Each Elf has made a <em>claim</em> about which area of fabric would be ideal for Santa's suit.  All claims have an ID and consist of a single rectangle with edges parallel to the edges of the fabric.  Each claim's rectangle is defined as follows:</p>
<ul>
<li>The number of inches between the left edge of the fabric and the left edge of the rectangle.</li>
<li>The number of inches between the top edge of the fabric and the top edge of the rectangle.</li>
<li>The width of the rectangle in inches.</li>
<li>The height of the rectangle in inches.</li>
</ul>
<p>A claim like <code>#123 @ 3,2: 5x4</code> means that claim ID <code>123</code> specifies a rectangle <code>3</code> inches from the left edge, <code>2</code> inches from the top edge, <code>5</code> inches wide, and <code>4</code> inches tall. Visually, it claims the square inches of fabric represented by <code>#</code> (and ignores the square inches of fabric represented by <code>.</code>) in the diagram below:</p>
<pre><code>...........
...........
...#####...
...#####...
...#####...
...#####...
...........
...........
...........
</code></pre>
<p>The problem is that many of the claims <em>overlap</em>, causing two or more claims to cover part of the same areas.  For example, consider the following claims:</p>
<pre><code>#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2
</code></pre>
<p>Visually, these claim the following areas:</p>
<pre><code>........
...2222.
...2222.
.11XX22.
.11XX22.
.111133.
.111133.
........
</code></pre>
<p>The four square inches marked with <code>X</code> are claimed by <em>both <code>1</code> and <code>2</code></em>. (Claim <code>3</code>, while adjacent to the others, does not overlap either of them.)</p>
<p>If the Elves all proceed with their own plans, none of them will have enough fabric. <em>How many square inches of fabric are within two or more claims?</em></p>
</article>
<article class="day-desc"><h2 id="part2">--- Part Two ---</h2><p>Amidst the chaos, you notice that exactly one claim doesn't overlap by even a single square inch of fabric with any other claim. If you can somehow draw attention to it, maybe the Elves will be able to make Santa's suit after all!</p>
<p>For example, in the claims above, only claim <code>3</code> is intact after all claims are made.</p>
<p><em>What is the ID of the only claim that doesn't overlap?</em></p>
</article>
