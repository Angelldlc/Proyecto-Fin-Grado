using Microsoft.AspNetCore.Mvc;
using OurTravelCities_BL;
using OurTravelCities_Entities;
using System;
using System.Collections.Generic;
using System.Net;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace OurTravelCities_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CitiesController : ControllerBase
    {
        // GET: api/<CitiesController>
        [HttpGet]
        public IEnumerable<City> Get()
        {
            List<City> listaPersonas;
            try
            {
                listaPersonas = clsListadosCityBL.getCitiesBL();
            }
            catch (Exception e)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.ServiceUnavailable);
            }
            if (listaPersonas == null || listaPersonas.Count == 0)
            {
                throw new System.Web.Http.HttpResponseException(HttpStatusCode.NoContent);
            }
            return listaPersonas;
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
