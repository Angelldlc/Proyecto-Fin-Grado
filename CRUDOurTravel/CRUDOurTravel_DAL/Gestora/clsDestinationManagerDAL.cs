using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Gestora
{
    public class clsDestinationManagerDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Adds an <typeparamref name="clsDestination"/> in a database.
        /// <br></br>
        /// <paramref name="oDestination"/> must not be null.
        /// </summary>
        /// <param name="oDestination"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int insertDestination(clsDestination oDestination)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("INSERT INTO OTD_Destinations (TripPlanningId, CityName, AcommodationCosts, TransportationCosts, FoodCosts, TourismCosts, Description, StartDate, EndDate, TravelStay, TouristAttractions) "
                + "VALUES(@tripPlanningId, @cityName, @acommodationCosts, @transportationCosts, @foodCosts, @tourismCosts, @description, @startDate, @endDate, @travelStay, @touristAttractions)");

            command.Parameters.AddWithValue("@tripPlanningId", oDestination.TripPlanningId);
            command.Parameters.AddWithValue("@cityName", oDestination.CityName);
            command.Parameters.AddWithValue("@acommodationCosts", oDestination.AcommodationCosts);
            command.Parameters.AddWithValue("@transportationCosts", oDestination.TransportationCosts);
            command.Parameters.AddWithValue("@foodCosts", oDestination.FoodCosts);
            command.Parameters.AddWithValue("@tourismCosts", oDestination.TourismCosts);
            command.Parameters.AddWithValue("@description", oDestination.Description);
            command.Parameters.AddWithValue("@startDate", oDestination.StartDate);
            command.Parameters.AddWithValue("@endDate", oDestination.EndDate);
            command.Parameters.AddWithValue("@travelStay", oDestination.TravelStay);
            command.Parameters.AddWithValue("@touristAttractions", oDestination.TouristAttractions);

            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                rowsAffected = command.ExecuteNonQuery();
            }
            catch
            {
                throw;
            }
            finally
            {
                myConnection.closeConnection();
            }
            return rowsAffected;
        }

        /// <summary>
        /// Edits an <typeparamref name="clsDestination"/> in a database.
        /// <br></br>
        /// <paramref name="oDestination"/> must not be null.
        /// </summary>
        /// <param name="oDestination"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int modifyDestination(clsDestination oDestination)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("UPDATE OTD_Destinations" +
                                        "SET CityName = @cityName, " + //TODO Ver si es necesario el nombre de la ciudad destino
                                            "AcommodationCosts = @acommodationCosts " +
                                            "TransportationCosts = @transportationCosts " +
                                            "FoodCosts = @foodCosts " +
                                            "TourismCosts = @tourismCosts " +
                                            "Description = @description " +
                                            "StartDate = @startDate " +
                                            "EndDate = @endDate " +
                                            "TravelStay = @travelStay " +
                                            "TouristAttractions = @totalCost " +
                                        "WHERE TripPlanningId = @touristAttractions");

            command.Parameters.AddWithValue("@tripPlanningId", oDestination.TripPlanningId);
            command.Parameters.AddWithValue("@cityName", oDestination.CityName);
            command.Parameters.AddWithValue("@acommodationCosts", oDestination.AcommodationCosts);
            command.Parameters.AddWithValue("@transportationCosts", oDestination.TransportationCosts);
            command.Parameters.AddWithValue("@foodCosts", oDestination.FoodCosts);
            command.Parameters.AddWithValue("@tourismCosts", oDestination.TourismCosts);
            command.Parameters.AddWithValue("@description", oDestination.Description);
            command.Parameters.AddWithValue("@startDate", oDestination.StartDate);
            command.Parameters.AddWithValue("@endDate", oDestination.EndDate);
            command.Parameters.AddWithValue("@travelStay", oDestination.TravelStay);
            command.Parameters.AddWithValue("@touristAttractions", oDestination.TouristAttractions);

            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                rowsAffected = command.ExecuteNonQuery();
            }
            catch
            {
                throw;
            }
            finally
            {
                myConnection.closeConnection();
            }
            return rowsAffected;
        }

        /// <summary>
        /// Deletes a <typeparamref name="clsDestination"/> from a database.
        /// <br></br>
        /// <paramref name="idDestinationToDelete"/> must not be "". 
        /// </summary>
        /// <param name="idDestinationToDelete"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int deleteDestination(int idDestinationToDelete)
        {
            int rowsAffected;
            SqlCommand command = new SqlCommand("DELETE FROM OTD_Destinations WHERE TripPlanningId = @idTripToDelete");//TODO Ver si evaluar el id del viaje y del destino
            command.Parameters.AddWithValue("@idTripToDelete", idDestinationToDelete);
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                rowsAffected = command.ExecuteNonQuery();
            }
            catch
            {
                throw;
            }
            finally
            {
                myConnection.closeConnection();
            }

            return rowsAffected;
        }
    }
}
