using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Listados
{
    public class clsTripPlanningListsDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Method that brings back a <typeparamref name="clsTripPlanning"/> list from a database. 
        /// </summary>
        /// <returns>List (<typeparamref name="clsTripPlannig"/>)</returns>
        public List<clsTripPlanning> getCompleteTripPlanningsListDAL()
        {
            List<clsTripPlanning> tripPlanningsList = new List<clsTripPlanning>();
            clsTripPlanning tripPlanning;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM OTD_Users");
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        tripPlanning = constructTripPlanning(reader);
                        tripPlanningsList.Add(tripPlanning);
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
            return tripPlanningsList;
        }

        /// <summary>
        /// Method that brings back a <typeparamref name="clsTripPlanning"/> searched by ID. 
        /// <br></br>
        /// <paramref name="tripPlanningId"/> must not be "".
        /// </summary>
        /// <param name="tripPlanningId"></param>
        /// <returns><typeparamref name="clsTripPlanning"/></returns>
        public clsTripPlanning getTripPlanningDAL(string tripPlanningId)
        {
            clsTripPlanning tripPlanning = null;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM OTD_TripPlanning WHERE TripPlanningId = @tripPlanningId");
            command.Parameters.AddWithValue("@tripPlanningId", tripPlanningId);
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    reader.Read();
                    tripPlanning = constructTripPlanning(reader);
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
            return tripPlanning;

        }

        /// <summary>
        /// Method that constructs a <typeparamref name="clsTripPlanning"/> from the read fields of the database.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns><typeparamref name="clsTripPlanning"/></returns>
        private clsTripPlanning constructTripPlanning(SqlDataReader reader)
        {
            clsTripPlanning constructedTripPlanning = new clsTripPlanning();

            constructedTripPlanning.TripPlanningId = (string)reader["TripPlanningId"];
            constructedTripPlanning.Name = (string)reader["\"Name\""];
            constructedTripPlanning.StartDate = (DateTime)reader["StartDate"];
            constructedTripPlanning.EndDate = (DateTime)reader["EndDate"];
            constructedTripPlanning.TotalCost = (double)reader["TotalCost"];

            return constructedTripPlanning;
        }
    }
}
