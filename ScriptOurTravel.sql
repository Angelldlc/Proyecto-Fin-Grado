USE master
GO
CREATE DATABASE OurTravelDatabase
GO
USE OurTravelDatabase
GO
CREATE TABLE OTD_Users(
	UserId UniqueIdentifier NOT NULL CONSTRAINT PK_OTD_User PRIMARY KEY,
	Username varchar(50) NOT NULL,
	Photo varbinary(MAX) NULL
)
GO

CREATE TABLE OTD_TripPlannings(
	TripPlanningId UniqueIdentifier NOT NULL CONSTRAINT PK_OTD_TripPlanning PRIMARY KEY,
	"Name" varchar(50) NOT NULL,
	StartDate smalldatetime NULL,
	EndDate smalldatetime NULL,
	TotalCost smallmoney NULL
)
GO

CREATE TABLE OTD_UsersTrips(
	UserId Uniqueidentifier NOT NULL CONSTRAINT FK_OTD_User FOREIGN KEY REFERENCES OTD_Users(UserId) ON delete no action ON update cascade,
	TripPlanningId Uniqueidentifier NOT NULL CONSTRAINT FK_OTD_TripPlanning FOREIGN KEY REFERENCES OTD_TripPlannings(TripPlanningId) ON delete no action ON update cascade,
	IsOrganizer BIT NOT NULL,
	IsCreator BIT NOT NULL,
	PRIMARY KEY (UserId, TripPlanningId)
)
GO

CREATE TABLE OTD_Destinations(
	TripPlanningId UniqueIdentifier NOT NULL CONSTRAINT FK_OTD_TripPlanning FOREIGN KEY REFERENCES OTD_TripPlannings(TripPlanningId) ON delete cascade ON update cascade CONSTRAINT PK_OTD_TripPlanning PRIMARY KEY,
	CityName varchar(50) NOT NULL CONSTRAINT UQ_OTD_Destination UNIQUE,
	AcommodationCosts smallmoney NULL,
	TransportationCosts smallmoney NULL,
	FoodCosts smallmoney NULL,
	TourismCosts smallmoney NULL,
	"Description" varchar(MAX) NOT NULL,
	StartDate smalldatetime NULL,
	EndDate smalldatetime NULL,
	TravelStay varchar(100) NULL,
	TouristAttractions varchar(MAX) NULL
)
GO