using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Gestora
{
    public class clsTripPlanningManagerDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Adds an <typeparamref name="clsTripPlanning"/> in a database.
        /// <br></br>
        /// <paramref name="oTrip"/> must not be null.
        /// </summary>
        /// <param name="oTrip"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int insertTripPlanning(clsTripPlanning oTrip)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("INSERT INTO OTD_TripPlannings (TripPlanningId, \"Name\", StartDate, EndDate, TotalCost) "
                + "VALUES(@tripPlanningId, @name, @startDate, @endDate, @totalCost)");

            command.Parameters.AddWithValue("@tripPlanningId", oTrip.TripPlanningId);
            command.Parameters.AddWithValue("@name", oTrip.Name);
            command.Parameters.AddWithValue("@startDate", oTrip.StartDate);
            command.Parameters.AddWithValue("@endDate", oTrip.EndDate);
            command.Parameters.AddWithValue("@totalCost", oTrip.TotalCost);

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
        /// Edits an <typeparamref name="clsTripPlanning"/> in a database.
        /// <br></br>
        /// <paramref name="oTrip"/> must not be null.
        /// </summary>
        /// <param name="oTrip"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int modifyTripPlanning(clsTripPlanning oTrip)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("UPDATE OTD_TripPlannings" +
                                        "SET \"Name\" = @name, " +
                                            "StartDate = @startDate " +
                                            "EndDate = @endDate " +
                                            "TotalCost = @totalCost " +
                                        "WHERE TripPlanningId = @TripPlanningId");

            command.Parameters.AddWithValue("@tripPlanningId", oTrip.TripPlanningId);
            command.Parameters.AddWithValue("@name", oTrip.Name);
            command.Parameters.AddWithValue("@startDate", oTrip.StartDate);
            command.Parameters.AddWithValue("@endDate", oTrip.EndDate);
            command.Parameters.AddWithValue("@totalCost", oTrip.TotalCost);

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
        /// Deletes a <typeparamref name="clsTripPlanning"/> from a database.
        /// <br></br>
        /// <paramref name="idTripToDelete"/> must not be "". 
        /// </summary>
        /// <param name="idTripToDelete"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int deleteTripPlanning(int idTripToDelete)
        {
            int rowsAffected;
            SqlCommand command = new SqlCommand("DELETE FROM OTD_TripPlannings WHERE TripPlanningId = @idTripToDelete");
            command.Parameters.AddWithValue("@idTripToDelete", idTripToDelete);
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
