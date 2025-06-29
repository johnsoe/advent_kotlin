<article class="day-desc"><h2>--- Day 6: Chronal Coordinates ---</h2><p>The device on your wrist beeps several times, and once again you feel like you're falling.</p>
<p>"<span title="Why is the situation always critical? Why can't the situation just be boring for once?">Situation critical</span>," the device announces. "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."</p>
<p>The device then produces a list of coordinates (your puzzle input). Are they places it thinks are safe or dangerous? It recommends you check manual page 729. The Elves did not give you a manual.</p>
<p><em>If they're dangerous,</em> maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.</p>
<p>Using only the <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a>, determine the <em>area</em> around each coordinate by counting the number of <a href="https://en.wikipedia.org/wiki/Integer">integer</a> X,Y locations that are <em>closest</em> to that coordinate (and aren't <em>tied in distance</em> to any other coordinate).</p>
<p>Your goal is to find the size of the <em>largest area</em> that isn't infinite. For example, consider the following list of coordinates:</p>
<pre><code>1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
</code></pre>
<p>If we name these coordinates <code>A</code> through <code>F</code>, we can draw them on a grid, putting <code>0,0</code> at the top left:</p>
<pre><code>..........
.A........
..........
........C.
...D......
.....E....
.B........
..........
..........
........F.
</code></pre>
<p>This view is partial - the actual grid extends infinitely in all directions.  Using the Manhattan distance, each location's closest coordinate can be determined, shown here in lowercase:</p>
<pre><code>aaaaa.cccc
a<em>A</em>aaa.cccc
aaaddecccc
aadddecc<em>C</em>c
..d<em>D</em>deeccc
bb.de<em>E</em>eecc
b<em>B</em>b.eeee..
bbb.eeefff
bbb.eeffff
bbb.ffff<em>F</em>f
</code></pre>
<p>Locations shown as <code>.</code> are equally far from two or more coordinates, and so they don't count as being closest to any.</p>
<p>In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here, their areas extend forever outside the visible grid. However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17 (both including the coordinate's location itself).  Therefore, in this example, the size of the largest area is <em>17</em>.</p>
<p><em>What is the size of the largest area</em> that isn't infinite?</p>
</article>
<article class="day-desc"><h2 id="part2">--- Part Two ---</h2><p>On the other hand, <em>if the coordinates are safe</em>, maybe the best you can do is try to find a <em>region</em> near as many coordinates as possible.</p>
<p>For example, suppose you want the sum of the <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a> to all of the coordinates to be <em>less than 32</em>.  For each location, add up the distances to all of the given coordinates; if the total of those distances is less than 32, that location is within the desired region. Using the same coordinates as above, the resulting region looks like this:</p>
<pre><code>..........
.A........
..........
...#<em>#</em>#..C.
..#D###...
..###E#...
.B.###....
..........
..........
........F.
</code></pre>
<p>In particular, consider the highlighted location <code>4,3</code> located at the top middle of the region. Its calculation is as follows, where <code>abs()</code> is the <a href="https://en.wikipedia.org/wiki/Absolute_value">absolute value</a> function:</p>
<ul>
<li>Distance to coordinate A: <code>abs(4-1) + abs(3-1) = &nbsp;5</code></li>
<li>Distance to coordinate B: <code>abs(4-1) + abs(3-6) = &nbsp;6</code></li>
<li>Distance to coordinate C: <code>abs(4-8) + abs(3-3) = &nbsp;4</code></li>
<li>Distance to coordinate D: <code>abs(4-3) + abs(3-4) = &nbsp;2</code></li>
<li>Distance to coordinate E: <code>abs(4-5) + abs(3-5) = &nbsp;3</code></li>
<li>Distance to coordinate F: <code>abs(4-8) + abs(3-9) = 10</code></li>
<li>Total distance: <code>5 + 6 + 4 + 2 + 3 + 10 = 30</code></li>
</ul>
<p>Because the total distance to all coordinates (<code>30</code>) is less than 32, the location is <em>within</em> the region.</p>
<p>This region, which also includes coordinates D and E, has a total size of <em>16</em>.</p>
<p>Your actual region will need to be much larger than this example, though, instead including all locations with a total distance of less than <em>10000</em>.</p>
<p><em>What is the size of the region containing all locations which have a total distance to all given coordinates of less than 10000?</em></p>
</article>
