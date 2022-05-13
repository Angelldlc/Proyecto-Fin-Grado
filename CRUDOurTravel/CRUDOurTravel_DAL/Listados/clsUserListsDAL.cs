using CRUDOurTravel_DAL.Conexion;
using CRUDOurTravel_Entities;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace CRUDOurTravel_DAL.Listados
{
    public class clsUserListsDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Method that brings back a <typeparamref name="clsUser"/> list from a database. 
        /// </summary>
        /// <returns>List (<typeparamref name="clsUser"/>)</returns>
        public static List<clsUser> getCompleteUsersListDAL()
        {
            List<clsUser> usersList = new List<clsUser>();
            clsUser user;
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
                        user = constructUser(reader);
                        usersList.Add(user);
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
            return usersList;
        }

        /// <summary>
        /// Method that brings back a <typeparamref name="clsUser"/> searched by ID. 
        /// <br></br>
        /// <paramref name="userId"/> must be higher than 0.
        /// </summary>
        /// <param name="userId"></param>
        /// <returns><typeparamref name="clsUser"/></returns>
        public static clsUser getUserDAL(string userId)
        {
            clsUser user = null;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM OTD_Users WHERE UserId = @userId");
            command.Parameters.AddWithValue("@userId", userId);
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    reader.Read();
                    user = constructUser(reader);
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
            return user;

        }

        /// <summary>
        /// Method that constructs a <typeparamref name="clsUser"/> from the read fields of the database.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns><typeparamref name="clsUser"/></returns>
        private static clsUser constructUser(SqlDataReader reader)
        {
            clsUser constructedUser = new clsUser();

            constructedUser.UserId = (string)reader["UserId"];
            constructedUser.Username = (string)reader["Username"];
            constructedUser.Photo = reader["Photo"] != (DBNull.Value) ? (byte[])reader["Foto"] : null;

            return constructedUser;
        }
    }
}
