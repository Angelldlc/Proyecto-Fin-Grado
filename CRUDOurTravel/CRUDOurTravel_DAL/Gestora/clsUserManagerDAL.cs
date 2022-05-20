using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Gestora
{
    public class clsUserManagerDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Adds an <typeparamref name="clsUser"/> in a database.
        /// <br></br>
        /// <paramref name="oUser"/> must not be null.
        /// </summary>
        /// <param name="oUser"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int insertUser(clsUser oUser)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("INSERT INTO OTD_Users (UserId, Username, Photo) "
                + "VALUES(@userId, @username, @photo)");

            command.Parameters.AddWithValue("@userId", oUser.UserId);
            command.Parameters.AddWithValue("@username", oUser.Username);
            command.Parameters.AddWithValue("@photo", (oUser.Photo == null) ? System.Data.SqlTypes.SqlBinary.Null : oUser.Photo);

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
        /// Edits an <typeparamref name="clsUser"/> in a database.
        /// <br></br>
        /// <paramref name="oUser"/> must not be null.
        /// </summary>
        /// <param name="oUser"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int modifyUser(clsUser oUser)
        {
            int rowsAffected;

            SqlCommand command = null;

            command = new SqlCommand("UPDATE OTD_Users" +
                                        "SET Username = @username, " +
                                            "Photo = @photo " +
                                        "WHERE UserId = @userId");

            command.Parameters.AddWithValue("@userId", oUser.UserId);
            command.Parameters.AddWithValue("@username", oUser.Username);
            command.Parameters.AddWithValue("@photo", (oUser.Photo == null) ? System.Data.SqlTypes.SqlBinary.Null : oUser.Photo);

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
        /// Deletes a <typeparamref name="clsUser"/> from a database.
        /// <br></br>
        /// <paramref name="idUserToDelete"/> must not be "". 
        /// </summary>
        /// <param name="idUserToDelete"></param>
        /// <returns>Number of rows affected in the database.</returns>
        public int deleteUser(int idUserToDelete)
        {
            int rowsAffected;
            SqlCommand command = new SqlCommand("DELETE FROM OTD_Users WHERE UserId = @idUserToDelete");
            command.Parameters.AddWithValue("@idUserToDelete", idUserToDelete);
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
