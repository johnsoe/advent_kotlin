<article class="day-desc"><h2>--- Day 4: Repose Record ---</h2><p>You've <span title="Yes, 'sneaked'. 'Snuck' didn't appear in English until the 1800s.">sneaked</span> into another supply closet - this time, it's across from the prototype suit manufacturing lab. You need to sneak inside and fix the issues with the suit, but there's a guard stationed outside the lab, so this is as close as you can safely get.</p>
<p>As you search the closet for anything that might help, you discover that you're not the first person to want to sneak in.  Covering the walls, someone has spent an hour starting every midnight for the past few months secretly observing this guard post!  They've been writing down the ID of <em>the one guard on duty that night</em> - the Elves seem to have decided that one guard was enough for the overnight shift - as well as when they fall asleep or wake up while at their post (your puzzle input).</p>
<p>For example, consider the following records, which have already been organized into chronological order:</p>
<pre><code>[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
</code></pre>
<p>Timestamps are written using <code>year-month-day hour:minute</code> format. The guard falling asleep or waking up is always the one whose shift most recently started. Because all asleep/awake times are during the midnight hour (<code>00:00</code> - <code>00:59</code>), only the minute portion (<code>00</code> - <code>59</code>) is relevant for those events.</p>
<p>Visually, these records show that the guards are asleep at these times:</p>
<pre><code>Date   ID   Minute
            000000000011111111112222222222333333333344444444445555555555
            012345678901234567890123456789012345678901234567890123456789
11-01  #10  .....####################.....#########################.....
11-02  #99  ........................................##########..........
11-03  #10  ........................#####...............................
11-04  #99  ....................................##########..............
11-05  #99  .............................................##########.....
</code></pre>
<p>The columns are Date, which shows the month-day portion of the relevant day; ID, which shows the guard on duty that day; and Minute, which shows the minutes during which the guard was asleep within the midnight hour.  (The Minute column's header shows the minute's ten's digit in the first row and the one's digit in the second row.) Awake is shown as <code>.</code>, and asleep is shown as <code>#</code>.</p>
<p>Note that guards count as asleep on the minute they fall asleep, and they count as awake on the minute they wake up. For example, because Guard #10 wakes up at 00:25 on 1518-11-01, minute 25 is marked as awake.</p>
<p>If you can figure out the guard most likely to be asleep at a specific time, you might be able to trick that guard into working tonight so you can have the best chance of sneaking in.  You have two strategies for choosing the best guard/minute combination.</p>
<p><em>Strategy 1:</em> Find the guard that has the most minutes asleep. What minute does that guard spend asleep the most?</p>
<p>In the example above, Guard #10 spent the most minutes asleep, a total of 50 minutes (20+25+5), while Guard #99 only slept for a total of 30 minutes (10+10+10). Guard #<em>10</em> was asleep most during minute <em>24</em> (on two days, whereas any other minute the guard was asleep was only seen on one day).</p>
<p>While this example listed the entries in chronological order, your entries are in the order you found them. You'll need to organize them before they can be analyzed.</p>
<p><em>What is the ID of the guard you chose multiplied by the minute you chose?</em> (In the above example, the answer would be <code>10 * 24 = 240</code>.)</p>
</article>
<article class="day-desc"><h2 id="part2">--- Part Two ---</h2><p><em>Strategy 2:</em> Of all guards, which guard is most frequently asleep on the same minute?</p>
<p>In the example above, Guard #<em>99</em> spent minute <em>45</em> asleep more than any other guard or minute - three times in total. (In all other cases, any guard spent any minute asleep at most twice.)</p>
<p><em>What is the ID of the guard you chose multiplied by the minute you chose?</em> (In the above example, the answer would be <code>99 * 45 = 4455</code>.)</p>
</article>
