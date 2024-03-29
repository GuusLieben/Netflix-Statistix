
/* All CRUD queries for the Netflix Statistix application */


/* Entity Account */

/* CREATE */
	INSERT INTO Account (isAdmin, Email, Straatnaam, Huisnummer, Toevoeging, Woonplaats, Wachtwoord)
	VALUES ([Boolean 1 or 0],
			'Email', 
			'Straatnaam', 
			[int Huisnummer], 
			'Toevoeging', 
			'Woonplaats', 
			'Wachtwoord')

/* SELECT */
	SELECT AccountID, isAdmin, Email, Straatnaam, Huisnummer, Toevoeging, Woonplaats, Wachtwoord
	FROM Account
	WHERE AccountId = [int AccountIdValue]

/* UPDATE */
	UPDATE Account
	SET isAdmin = [newBoolean 1 or 2],
		Email = 'newEmail',
		Straatnaam = 'newStraatnaam',
		Huisnummer = [int newHuisnummer],
		Toevoeging = 'newToevoeging',
		Woonplaats = 'newWoonplaats',
		Wachtwoord = 'newWachtwoord'
	WHERE AccountId = [int AccountIdValue]
		
/* DELETE */
	DELETE FROM Account
	WHERE AccountId = [int AccountIdValue]
	
	


/* Entity Profile (user) */

/* CREATE */
	INSERT INTO Users (AccountId, Name, EpisodesWatched, FilmsWatched, Birthday)
	VALUES ([int AccountId],
			'Name',
			[int EpisodesWatched],
			[int FilmsWatched],
			[Date Birthday])

/* READ */
	SELECT UserId, AccountId, Name, EpisodesWatched, FilmsWatched, Birthday
	FROM Users
	WHERE UserId = [int UserIdValue]

/* UPDATE */
	UPDATE Users
	SET AccountId = [int newAccountId],
		Name = 'newName',
		EpisodesWatched = [int newEpisodesWatched],
		FilmsWatched = [int newFilmsWatched],
		Birthday = [Date newBirthday]
	WHERE UserId = [int UserIdValue]

/* DELETE */
	DELETE FROM Users
	WHERE UserId = [int UserIdValue]
	



/* Entity Episode */

/* CREATE */
	INSERT INTO Episode (SeasonId, Title, Duration, EpisodeNumber)
	VALUES ([int SeasonId], 
			'Title', 
			[time Duration], 
			[int EpisodeNumber])

/* READ */
	SELECT EpisodeId, SeasonId, Title, Duration, EpisodeNumber
	FROM Episode
	WHERE EpisodeId = [int EpisodeIdValue]

/* UPDATE */
	UPDATE Episode
	SET SeasonId = [int newSeasonId],
		Title = 'newTitle',
		Duration = [time newDuration],
		EpisodeNumber = [int newEpisodeNumber]
	WHERE EpisodeId = [int EpisodeIdValue]

/* DELETE */	
	DELETE FROM Episode
	WHERE EpisodeId = [int EpisodeIdValue]




/* Entity Film */

/* CREATE */
	INSERT INTO Film (Rating, LijktOp, LanguageCode, Title, Duration, Director)
	VALUES ('Rating', 
			'LijktOp', 
			'LanguageCode', 
			'Title', 
			[time Duration], 
			'Director')

	INSERT INTO Koppeltabel_GenreId_Film (FilmId, GenreId)
	VALUES ([int FilmId],
			[int GenreId])

/* READ */
	SELECT Film.FilmId, Rating, LijktOp, LanguageCode, Title, Duration, Director, Genre
	FROM Film
	JOIN Koppeltabel_GenreId_Film
		ON Film.FilmId = Koppeltabel_GenreId_Film.FilmId
	JOIN Genre
		ON Koppeltabel_GenreId_Film.GenreId = Genre.GenreId
	WHERE FilmId = [int FilmIdValue]

/* UPDATE */
	UPDATE Film 
	SET Rating = 'newRating',
		LijktOp = 'newLijktOp',
		LanguageCode = 'newLanguageCode',
		Title = 'newTitle',
		Duration = [time newDuration],
		Director = 'newDirector'
	WHERE FilmId = [int FilmIdValue]

	UPDATE Koppeltabel_GenreId_Film
	SET GenreId = [int newGenreId]
	WHERE FilmId = [int FilmIdValue]

/* DELETE */	
	DELETE FROM Film
	WHERE FilmId = [int FilmIdValue]

	DELETE FROM Koppeltabel_GenreId_Film
	WHERE FilmId = [int FilmIdValue]


/* Entity Season */

/* CREATE */
	INSERT INTO Season (SerieId, Title, SeasonNumber)
	VALUES ([int SerieId],
			'Title',
			[int SeasonNumber])

/* READ */
	SELECT SeasonId, SerieId, Title, SeasonNumber
	FROM Season
	WHERE SeasonId = [int SeasonIdValue]

/* UPDATE */
	UPDATE Season
	SET SerieId = [int newSerieId],
		Title = 'newTitle',
		SeasonNumber = [int newSeasonNumber]
	WHERE SeasonId = [int SeasonIdValue]

/* DELETE */
	DELETE FROM Season
	WHERE SeasonId = [int SeasonIdValue]




 /* Entity Serie */

/* CREATE */
	INSERT INTO Serie (Title, AmountOfSeasons, LijktOp, LanguageCode, Rating)
	VALUES ('Title',
			[int AmountOfSeasons],
			'LijktOp',
			'LanguageCode',
			'Rating')

	INSERT INTO Koppeltabel_Serie_Genre (SerieId, GenreId)
	VALUES ([int SerieId],
			[int GenreId])

/* READ */
	SELECT Serie.SerieId, Title, AmountOfSeasons, LijktOp, LanguageCode, Rating, Genre
	FROM Serie
	JOIN Koppeltabel_Serie_Genre
		ON Serie.SerieId = Koppeltabel_Serie_Genre.SerieId
	JOIN Genre
		ON Koppeltabel_Serie_Genre.GenreId = Genre.GenreId
	WHERE SerieId = [int SerieIdValue]

/* UPDATE */
	UPDATE Serie
	SET Title = 'newTitle',
		AmountOfSeasons = [int newAmountOfSeasons],
		LijktOp = 'newLijktOp',
		LanguageCode = 'newLanguageCode',
		Rating = 'newRating'
	WHERE SerieId = [int SerieIdValue]

	UPDATE Koppeltabel_Serie_Genre
	SET GenreId = [int newGenreId]
	WHERE SerieId = [int SerieIdValue]

/* DELETE */
	DELETE FROM Serie
	WHERE SerieId = [int SerieIdValue]

	DELETE FROM Koppeltabel_Serie_Genre
	WHERE SerieId = [int SerieIdValue]
 



 /* Entity Rating */

/* CREATE */
	INSERT INTO Rating (MPAA, Rating)
	VALUES ('MPAA', 
			'Rating')

/* READ */
	SELECT MPAA, Rating
	FROM Rating
	WHERE MPAA = 'MPAAValue'

/* UPDATE */
	UPDATE Rating
	SET MPAA = 'newMPAA',
		Rating = 'newRating'
	WHERE MPAA = 'MPAAValue'

/* DELETE */
	DELETE FROM Rating
	WHERE MPAA = 'MPAAValue'
 



 /* Entity Genre */

/* CREATE */
	INSERT INTO Genre (Genre)
	VALUES ('Genre')

/* READ */
	SELECT GenreId, Genre
	FROM Genre
	WHERE GenreId = [int GenreIdValue]

/* UPDATE */
	UPDATE Genre
	SET Genre = 'newGenreId'
	WHERE GenreId = [int GenreIdValue]

/* DELETE */
	DELETE FROM Genre
	WHERE GenreId = [int GenreIdValue]
 



 /* Entity Language */

/* CREATE */
	INSERT INTO Language (LanguageCode, Language)
	VALUES ('LanguageCode', 
			'Language')

/* READ */
	SELECT LanguageCode, Language
	FROM Language
	WHERE LanguageCode = 'LanguageCodeValue'

/* UPDATE */
	UPDATE Language
	SET LanguageCode = 'newLanguageCode',
		Language = 'newLanguage'
	WHERE LanguageCode = 'LanguageCodeValue'

/* DELETE */
	DELETE FROM Language
	WHERE LanguageCode = 'LanguageCodeValue'

 
