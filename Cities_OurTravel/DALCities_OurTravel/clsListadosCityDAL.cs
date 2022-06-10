using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using EntitiesCities_OurTravel;


namespace DALCities_OurTravel
{
    public class clsListadosCityDAL
    {
        private static clsMyConnection myConnection = new clsMyConnection();

        /// <summary>
        /// Method that brings back a <typeparamref name="City"/> list from a database. 
        /// </summary>
        /// <returns>List (<typeparamref name="City"/>)</returns>
        public static List<City> getCitiesDAL()
        {
            List<City> cities = new List<City>();
            City city;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM City");
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        city = constructCity(reader);
                        cities.Add(city);
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
            return cities;
        }

        /// <summary>
        /// Method that brings back a <typeparamref name="City"/> searched by ID. 
        /// <br></br>
        /// <paramref name="name"/> must not be null or empty.
        /// </summary>
        /// <param name="name"></param>
        /// <returns><typeparamref name="City"/></returns>
        public static City getCityDAL(string name)
        {
            City city = null;
            SqlDataReader reader;
            SqlCommand command = new SqlCommand("SELECT * FROM City WHERE name = @name");
            command.Parameters.AddWithValue("@name", name);
            try
            {
                myConnection.openConnection();
                command.Connection = myConnection.Connection;
                reader = command.ExecuteReader();
                if (reader.HasRows)
                {
                    reader.Read();
                    city = constructCity(reader);
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
            return city;

        }

        /// <summary>
        /// Method that constructs a <typeparamref name="City"/> from the read fields of the database.
        /// </summary>
        /// <param name="lector"></param>
        /// <returns><typeparamref name="City"/></returns>
        private static City constructCity(SqlDataReader lector)
        {
            City constructedCity = new City
            {
                name = (string)lector["name"],
                photo = (string)lector["photo"]
            };

            return constructedCity;
        }
    }
}
