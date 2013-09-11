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


