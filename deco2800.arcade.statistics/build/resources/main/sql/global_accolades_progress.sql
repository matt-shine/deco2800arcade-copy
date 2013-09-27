CREATE OR REPLACE [Global_Accolades_Progress] AS
SELECT SUM(pa.score),
		ga.tag VARRCHAR(32) NOT NULL,
		ga.name VARCHAR(16) NOT NULL,
		ga.string VARCHAR(64) NOT NULL,
		ga.unit VARCHAR(16) NOT NULL,
		ga.modifier VARCHAR(64),
		ga.image VARCHAR(32)
FROM Player_Accolades AS pa, Global_Accolades AS ga, Accolades AS a
WHERE a.accolade_id = pa.accolade_id AND ga.tag = /** Need to put a

/** first i need to sum all the accolades together for each player. 
*	then sum all those values together based on the tags.

sum vale, group by accolade ID,
Then sum value again group by tag.

So combine players stats to form game_ID, Accolade_ID, Total, tag
then sum them all based on the tag.
**/

SELECT 
FROM accola






