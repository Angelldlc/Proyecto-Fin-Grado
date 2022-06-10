using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.Net;
using EntitiesCities_OurTravel;
using BLCities_OurTravel;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace APICities_OurTravel.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CitiesController : ControllerBase
    {
        // GET: api/<CitiesController>
        [HttpGet]
        public IEnumerable<City> Get()
        {
            List<City> cities;
            try
            {
                cities = clsListadosCityBL.getCitiesBL();
            }
            catch (Exception e)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.ServiceUnavailable);
            }
            if (cities == null || cities.Count == 0)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.NoContent);
            }
            return cities;
        }

        // GET api/<CitiesController>/5
        [HttpGet("{id}")]
        public City Get(string name)
        {
            City city;
            try
            {
                city = clsListadosCityBL.getCityBL(name);
            }
            catch (Exception e)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.ServiceUnavailable);
            }
            if (city == null)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.NoContent);
            }
            return city;
        }
    }
}
