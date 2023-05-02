USE [master]
GO
/****** Object:  Database [Hanoi]    Script Date: 2022-03-07 9:04:46 PM ******/
CREATE DATABASE [Hanoi]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Hanoi', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hanoi.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Hanoi_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hanoi_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Hanoi] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Hanoi].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Hanoi] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Hanoi] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Hanoi] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Hanoi] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Hanoi] SET ARITHABORT OFF 
GO
ALTER DATABASE [Hanoi] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Hanoi] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Hanoi] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Hanoi] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Hanoi] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Hanoi] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Hanoi] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Hanoi] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Hanoi] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Hanoi] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Hanoi] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Hanoi] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Hanoi] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Hanoi] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Hanoi] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Hanoi] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Hanoi] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Hanoi] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Hanoi] SET  MULTI_USER 
GO
ALTER DATABASE [Hanoi] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Hanoi] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Hanoi] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Hanoi] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Hanoi] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Hanoi] SET QUERY_STORE = OFF
GO
USE [Hanoi]
GO
/****** Object:  Table [dbo].[Score]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Score](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [nvarchar](50) NOT NULL,
	[Disks] [int] NOT NULL,
	[Pegs] [int] NULL,
	[Steps] [int] NULL,
	[Time] [time](7) NULL,
	[Date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [nvarchar](50) NOT NULL,
	[Pwd] [nvarchar](20) NOT NULL,
	[Name] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[usp_Score_Insert]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[usp_Score_Insert]
	@pUserId nvarchar(50) ,
	@pDisks int ,
	@pPegs int,
	@pSteps int,
	@pSeconds int
as
	Declare @vTime time;
	set @vTime = convert(char(8), dateadd(second, @pSeconds, ''), 114);
	insert into Score 
	(UserId ,Disks ,Pegs ,Steps ,Time ,Date)
	values(@pUserId, @pDisks, @pPegs, @pSteps, @vTime, GETDATE());
GO
/****** Object:  StoredProcedure [dbo].[usp_Score_Select]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE procedure [dbo].[usp_Score_Select]
	@pUserId nvarchar(50)
as
	if(@pUserId<>'')
		select 
			Score = u.Name + ' ' + convert(varchar, s.Time, 8)
		from Score s
		inner join Users u on s.UserId = u.Id
		where UserId = @pUserId;
	else
		select top 10 
			Score = u.Name + ' ' + convert(varchar, s.Time, 8)
		from Score s
		inner join Users u on s.UserId = u.Id
		order by Time;

GO
/****** Object:  StoredProcedure [dbo].[usp_Users_Insert]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create procedure [dbo].[usp_Users_Insert]
	@pId nvarchar(50),
	@pPwd nvarchar(20),
	@pName nvarchar(50),
	@pOutput nvarchar(50) output
as
	declare @vId nvarchar(50);

	select @vId = Id from Users where Id = @pId;

	if @vId <> ''
		set @pOutput = 'Invalid user';
	else
		begin
			insert into Users 
			values(@pId, @pPwd, @pName)
			set @pOutput = '';
		end
GO
/****** Object:  StoredProcedure [dbo].[usp_Users_Login]    Script Date: 2022-03-07 9:04:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[usp_Users_Login]
	@pId nvarchar(50),
	@pPwd nvarchar(20),
	@pName nvarchar(50) output
as
	declare @vId nvarchar(50);

	select @pName = Name 
	from Users 
	where Id = @pId
		and Pwd = @pPwd;
GO
USE [master]
GO
ALTER DATABASE [Hanoi] SET  READ_WRITE 
GO
