﻿using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Listados
{
    public class clsDestinationListsDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Method that brings back a <typeparamref name="clsDestination"/> list from a database. 
        /// </summary>
        /// <returns>List (<typeparamref name="clsDestination"/>)</returns>
        public List<clsDestination> getCompleteDestinationsListDAL()
        {
            List<clsDestination> destinationsList = new List<clsDestination>();
            clsDestination destination;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM OTD_Destinations");
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        destination = constructDestination(reader);
                        destinationsList.Add(destination);
                    }
                }
            }
            catch
            {
                throw;
            }
            finally
            {
                myConnection.closeConnection();
            }
            return destinationsList;
        }

        /// <summary>
        /// Method that brings back a <typeparamref name="clsDestination"/> searched by ID. 
        /// <br></br>
        /// <paramref name="tripPlanningId"/> must not be "".
        /// <paramref name="cityName"/> must not be "".
        /// </summary>
        /// <param name="tripPlanningId"></param>
        /// <param name="cityName"></param>
        /// <returns><typeparamref name="clsDestination"/></returns>
        public clsDestination getDestinationDAL(string tripPlanningId, string cityName)//TODO TripPlanningId
        {
            clsDestination destination = null;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM OTD_Destinations WHERE TripPlanningId = @tripPlanningId AND CityName = @cityName");//TODO lo mismo que arriba
            command.Parameters.AddWithValue("@tripPlanningId", tripPlanningId);
            command.Parameters.AddWithValue("@cityName", cityName);
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    reader.Read();
                    destination = constructDestination(reader);
                }
            }
            catch
            {
                throw;
            }
            finally
            {
                myConnection.closeConnection();
            }
            return destination;

        }

        /// <summary>
        /// Method that constructs a <typeparamref name="clsDestination"/> from the read fields of the database.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns><typeparamref name="clsDestination"/></returns>
        private clsDestination constructDestination(SqlDataReader reader)
        {
            clsDestination constructedDestination = new clsDestination();

            constructedDestination.TripPlanningId = (string)reader["TripPlanningId"];
            constructedDestination.CityName = (string)reader["CityName"];
            constructedDestination.AcommodationCosts = (double)reader["AcommodationCosts"];
            constructedDestination.TransportationCosts = (double)reader["TransportationCosts"];
            constructedDestination.FoodCosts = (double)reader["FoodCosts"];
            constructedDestination.TourismCosts = (double)reader["TourismCosts"];
            constructedDestination.Description = (string)reader["\"Description\""];
            constructedDestination.StartDate = (DateTime)reader["StartDate"];
            constructedDestination.EndDate = (DateTime)reader["EndDate"];
            constructedDestination.TravelStay = (string)reader["TravelStay"];
            constructedDestination.TouristAttractions = (string)reader["TouristAttractions"];

            return constructedDestination;
        }
    }
}
