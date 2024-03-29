USE [master]
GO
/****** Object:  Database [NetflixTrio]    Script Date: 7-12-2018 11:03:09 ******/
CREATE DATABASE [NetflixTrio]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'NetflixTrio', FILENAME = N'C:\SQLData\NetflixTrio.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'NetflixTrio_log', FILENAME = N'C:\SQLData\NetflixTrio_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [NetflixTrio] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [NetflixTrio].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [NetflixTrio] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [NetflixTrio] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [NetflixTrio] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [NetflixTrio] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [NetflixTrio] SET ARITHABORT OFF 
GO
ALTER DATABASE [NetflixTrio] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [NetflixTrio] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [NetflixTrio] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [NetflixTrio] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [NetflixTrio] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [NetflixTrio] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [NetflixTrio] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [NetflixTrio] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [NetflixTrio] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [NetflixTrio] SET  DISABLE_BROKER 
GO
ALTER DATABASE [NetflixTrio] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [NetflixTrio] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [NetflixTrio] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [NetflixTrio] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [NetflixTrio] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [NetflixTrio] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [NetflixTrio] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [NetflixTrio] SET RECOVERY FULL 
GO
ALTER DATABASE [NetflixTrio] SET  MULTI_USER 
GO
ALTER DATABASE [NetflixTrio] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [NetflixTrio] SET DB_CHAINING OFF 
GO
ALTER DATABASE [NetflixTrio] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [NetflixTrio] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [NetflixTrio] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'NetflixTrio', N'ON'
GO
ALTER DATABASE [NetflixTrio] SET QUERY_STORE = OFF
GO
USE [NetflixTrio]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 7-12-2018 11:03:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[AccountId] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](40) NOT NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[AccountId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_Account] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Admin]    Script Date: 7-12-2018 11:03:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admin](
	[AccountId] [int] NOT NULL,
	[EditAccounts] [bit] NOT NULL,
	[EditFilmsSeries] [bit] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Episode]    Script Date: 7-12-2018 11:03:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Episode](
	[SeasonId] [int] NOT NULL,
	[Title] [nvarchar](40) NOT NULL,
	[Duration] [time](7) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Film]    Script Date: 7-12-2018 11:03:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Film](
	[FilmId] [int] IDENTITY(1,1) NOT NULL,
	[Rating] [nvarchar](10) NOT NULL,
	[Genre] [nvarchar](50) NOT NULL,
	[LanguageCode] [nvarchar](5) NOT NULL,
	[Title] [nvarchar](40) NOT NULL,
	[Duration] [time](7) NOT NULL,
	[Director] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Film_1] PRIMARY KEY CLUSTERED 
(
	[FilmId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Genre]    Script Date: 7-12-2018 11:03:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Genre](
	[GenreId] [int] IDENTITY(1,1) NOT NULL,
	[Genre] [nvarchar](35) NOT NULL,
 CONSTRAINT [PK_Genre] PRIMARY KEY CLUSTERED 
(
	[GenreId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Koppeltabel_GenreId_Film]    Script Date: 7-12-2018 11:03:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Koppeltabel_GenreId_Film](
	[GenreId] [int] NOT NULL,
	[FilmId] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Koppeltabel_Serie_Genre]    Script Date: 7-12-2018 11:03:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Koppeltabel_Serie_Genre](
	[GenreId] [int] NOT NULL,
	[SerieId] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Language]    Script Date: 7-12-2018 11:03:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Language](
	[Language] [nvarchar](20) NOT NULL,
	[LanguageCode] [nvarchar](5) NOT NULL,
 CONSTRAINT [PK_Language] PRIMARY KEY CLUSTERED 
(
	[LanguageCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Rating]    Script Date: 7-12-2018 11:03:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rating](
	[Rating] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_Rating] PRIMARY KEY CLUSTERED 
(
	[Rating] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Season]    Script Date: 7-12-2018 11:03:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Season](
	[SeasonId] [int] IDENTITY(1,1) NOT NULL,
	[SerieId] [int] NOT NULL,
	[Title] [nvarchar](40) NOT NULL,
	[SeasonNumber] [int] NOT NULL,
	[AmountOfEpisodes] [int] NOT NULL,
 CONSTRAINT [PK_Season] PRIMARY KEY CLUSTERED 
(
	[SeasonId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Serie]    Script Date: 7-12-2018 11:03:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Serie](
	[SerieId] [int] IDENTITY(1,1) NOT NULL,
	[Genre] [nvarchar](50) NOT NULL,
	[LanguageCode] [nvarchar](5) NOT NULL,
	[Title] [nvarchar](40) NOT NULL,
	[Rating] [nvarchar](10) NULL,
	[AmountOfSeasons] [int] NOT NULL,
 CONSTRAINT [PK_Serie] PRIMARY KEY CLUSTERED 
(
	[SerieId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 7-12-2018 11:03:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[AccountId] [int] NOT NULL,
	[FilmsWatched] [int] NOT NULL,
	[SeriesWatched] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Admin]  WITH CHECK ADD  CONSTRAINT [FK_Admin_Account] FOREIGN KEY([AccountId])
REFERENCES [dbo].[Account] ([AccountId])
GO
ALTER TABLE [dbo].[Admin] CHECK CONSTRAINT [FK_Admin_Account]
GO
ALTER TABLE [dbo].[Episode]  WITH CHECK ADD  CONSTRAINT [FK_Episode_Season] FOREIGN KEY([SeasonId])
REFERENCES [dbo].[Season] ([SeasonId])
GO
ALTER TABLE [dbo].[Episode] CHECK CONSTRAINT [FK_Episode_Season]
GO
ALTER TABLE [dbo].[Film]  WITH CHECK ADD  CONSTRAINT [FK_Film_Language] FOREIGN KEY([LanguageCode])
REFERENCES [dbo].[Language] ([LanguageCode])
GO
ALTER TABLE [dbo].[Film] CHECK CONSTRAINT [FK_Film_Language]
GO
ALTER TABLE [dbo].[Film]  WITH CHECK ADD  CONSTRAINT [FK_Film_Rating] FOREIGN KEY([Rating])
REFERENCES [dbo].[Rating] ([Rating])
GO
ALTER TABLE [dbo].[Film] CHECK CONSTRAINT [FK_Film_Rating]
GO
ALTER TABLE [dbo].[Koppeltabel_GenreId_Film]  WITH CHECK ADD  CONSTRAINT [FK_Koppeltabel_GenreId_Film_Film] FOREIGN KEY([FilmId])
REFERENCES [dbo].[Film] ([FilmId])
GO
ALTER TABLE [dbo].[Koppeltabel_GenreId_Film] CHECK CONSTRAINT [FK_Koppeltabel_GenreId_Film_Film]
GO
ALTER TABLE [dbo].[Koppeltabel_GenreId_Film]  WITH CHECK ADD  CONSTRAINT [FK_Koppeltabel_GenreId_Film_Genre] FOREIGN KEY([GenreId])
REFERENCES [dbo].[Genre] ([GenreId])
GO
ALTER TABLE [dbo].[Koppeltabel_GenreId_Film] CHECK CONSTRAINT [FK_Koppeltabel_GenreId_Film_Genre]
GO
ALTER TABLE [dbo].[Koppeltabel_Serie_Genre]  WITH CHECK ADD  CONSTRAINT [FK_Koppeltabel_Serie_Genre_Genre] FOREIGN KEY([GenreId])
REFERENCES [dbo].[Genre] ([GenreId])
GO
ALTER TABLE [dbo].[Koppeltabel_Serie_Genre] CHECK CONSTRAINT [FK_Koppeltabel_Serie_Genre_Genre]
GO
ALTER TABLE [dbo].[Koppeltabel_Serie_Genre]  WITH CHECK ADD  CONSTRAINT [FK_Koppeltabel_Serie_Genre_Serie] FOREIGN KEY([SerieId])
REFERENCES [dbo].[Serie] ([SerieId])
GO
ALTER TABLE [dbo].[Koppeltabel_Serie_Genre] CHECK CONSTRAINT [FK_Koppeltabel_Serie_Genre_Serie]
GO
ALTER TABLE [dbo].[Season]  WITH CHECK ADD  CONSTRAINT [FK_Season_Serie] FOREIGN KEY([SerieId])
REFERENCES [dbo].[Serie] ([SerieId])
GO
ALTER TABLE [dbo].[Season] CHECK CONSTRAINT [FK_Season_Serie]
GO
ALTER TABLE [dbo].[Serie]  WITH CHECK ADD  CONSTRAINT [FK_Serie_Language] FOREIGN KEY([LanguageCode])
REFERENCES [dbo].[Language] ([LanguageCode])
GO
ALTER TABLE [dbo].[Serie] CHECK CONSTRAINT [FK_Serie_Language]
GO
ALTER TABLE [dbo].[Serie]  WITH CHECK ADD  CONSTRAINT [FK_Serie_Rating] FOREIGN KEY([Rating])
REFERENCES [dbo].[Rating] ([Rating])
GO
ALTER TABLE [dbo].[Serie] CHECK CONSTRAINT [FK_Serie_Rating]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [FK_User_Account] FOREIGN KEY([AccountId])
REFERENCES [dbo].[Account] ([AccountId])
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [FK_User_Account]
GO
USE [master]
GO
ALTER DATABASE [NetflixTrio] SET  READ_WRITE 
GO
