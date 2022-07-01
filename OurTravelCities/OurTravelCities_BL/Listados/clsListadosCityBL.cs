﻿using OurTravelCities_DAL;
using OurTravelCities_Entities;
using System;
using System.Collections.Generic;

namespace OurTravelCities_BL
{
    public class clsListadosCityBL
    {
        /// <summary>
        /// Method that brings back a <typeparamref name="City"/> list from a database. 
        /// </summary>
        /// <returns>List (<typeparamref name="City"/>)</returns>
        public static List<City> getCitiesBL()
        {
            try
            {
                return clsListadosCityDAL.getCitiesDAL();
            }
            catch
            {
                throw;
            }
        }

        /// <summary>
        /// Method that brings back a <typeparamref name="City"/> searched by ID. 
        /// <br></br>
        /// <paramref name="name"/> must not be null or empty.
        /// </summary>
        /// <param name="name"></param>
        /// <returns><typeparamref name="City"/></returns>
        public static City getCityBL(string name)
        {
            try
            {
                return clsListadosCityDAL.getCityDAL(name);
            }
            catch
            {
                throw;
            }
        }
    }
}