<article class="day-desc"><h2>--- Day 3: Squares With Three Sides ---</h2><p>Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for triangles.</p>
<p>Or are they?</p>
<p>The design document gives the side lengths of each triangle it describes, but... <code>5 10 25</code>?  Some of these aren't triangles. You can't help but mark the impossible ones.</p>
<p>In a valid triangle, the sum of any two sides must be larger than the remaining side.  For example, the "triangle" given above is <span title="Unless it's on a sphere!">impossible</span>, because <code>5 + 10</code> is not larger than <code>25</code>.</p>
<p>In your puzzle input, <em>how many</em> of the listed triangles are <em>possible</em>?</p>
</article>
<article class="day-desc"><h2 id="part2">--- Part Two ---</h2><p>Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three <em>vertically</em>.  Each set of three numbers in a column specifies a triangle.  Rows are unrelated.</p>
<p>For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:</p>
<pre><code>101 301 501
102 302 502
103 303 503
201 401 601
202 402 602
203 403 603
</code></pre>
<p>In your puzzle input, and instead reading by columns, <em>how many</em> of the listed triangles are <em>possible</em>?</p>
</article>
