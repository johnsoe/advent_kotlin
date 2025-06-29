<article class="day-desc"><h2>--- Day 5: Alchemical Reduction ---</h2><p>You've managed to sneak in to the prototype suit manufacturing lab.  The Elves are making decent progress, but are still struggling with the suit's size reduction capabilities.</p>
<p>While the very latest in 1518 alchemical technology might have solved their problem eventually, you can do better.  You scan the chemical composition of the suit's material and discover that it is formed by extremely long <a href="https://en.wikipedia.org/wiki/Polymer">polymers</a> (one of which is <span title="I've always wanted a polymer!">available</span> as your puzzle input).</p>
<p>The polymer is formed by smaller <em>units</em> which, when triggered, react with each other such that two adjacent units of the same type and opposite polarity are destroyed. Units' types are represented by letters; units' polarity is represented by capitalization.  For instance, <code>r</code> and <code>R</code> are units with the same type but opposite polarity, whereas <code>r</code> and <code>s</code> are entirely different types and do not react.</p>
<p>For example:</p>
<ul>
<li>In <code>aA</code>, <code>a</code> and <code>A</code> react, leaving nothing behind.</li>
<li>In <code>abBA</code>, <code>bB</code> destroys itself, leaving <code>aA</code>.  As above, this then destroys itself, leaving nothing.</li>
<li>In <code>abAB</code>, no two adjacent units are of the same type, and so nothing happens.</li>
<li>In <code>aabAAB</code>, even though <code>aa</code> and <code>AA</code> are of the same type, their polarities match, and so nothing happens.</li>
</ul>
<p>Now, consider a larger example, <code>dabAcCaCBAcCcaDA</code>:</p>
<pre><code>dabA<em>cC</em>aCBAcCcaDA  The first 'cC' is removed.
dab<em>Aa</em>CBAcCcaDA    This creates 'Aa', which is removed.
dabCBA<em>cCc</em>aDA      Either 'cC' or 'Cc' are removed (the result is the same).
dabCBAcaDA        No further actions can be taken.
</code></pre>
<p>After all possible reactions, the resulting polymer contains <em>10 units</em>.</p>
<p><em>How many units remain after fully reacting the polymer you scanned?</em> <span class="quiet">(Note: in this puzzle and others, the input is large; if you copy/paste your input, make sure you get the whole thing.)</span></p>
</article>
<article class="day-desc"><h2 id="part2">--- Part Two ---</h2><p>Time to improve the polymer.</p>
<p>One of the unit types is causing problems; it's preventing the polymer from collapsing as much as it should.  Your goal is to figure out which unit type is causing the most problems, remove all instances of it (regardless of polarity), fully react the remaining polymer, and measure its length.</p>
<p>For example, again using the polymer <code>dabAcCaCBAcCcaDA</code> from above:</p>
<ul>
<li>Removing all <code>A</code>/<code>a</code> units produces <code>dbcCCBcCcD</code>. Fully reacting this polymer produces <code>dbCBcD</code>, which has length 6.</li>
<li>Removing all <code>B</code>/<code>b</code> units produces <code>daAcCaCAcCcaDA</code>. Fully reacting this polymer produces <code>daCAcaDA</code>, which has length 8.</li>
<li>Removing all <code>C</code>/<code>c</code> units produces <code>dabAaBAaDA</code>. Fully reacting this polymer produces <code>daDA</code>, which has length 4.</li>
<li>Removing all <code>D</code>/<code>d</code> units produces <code>abAcCaCBAcCcaA</code>. Fully reacting this polymer produces <code>abCBAc</code>, which has length 6.</li>
</ul>
<p>In this example, removing all <code>C</code>/<code>c</code> units was best, producing the answer <em>4</em>.</p>
<p><em>What is the length of the shortest polymer you can produce</em> by removing all units of exactly one type and fully reacting the result?</p>
</article>
