using CRUDOurTravel_DAL.Gestora;
using CRUDOurTravel_DAL.Listados;
using CRUDOurTravel_Entities;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Net;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace CRUDOurTravel__API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DestinationsController : ControllerBase
    {
        // GET: api/<DestinationsController>
        [HttpGet]
        public ObjectResult Get()
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = null;
            try
            {
                result.Value = new clsDestinationListsDAL().getCompleteDestinationsListDAL();
                if ((result.Value as List<clsDestination>).Count == 0)
                {
                    result.StatusCode = (int)HttpStatusCode.NotFound;
                }
                else
                {
                    result.StatusCode = (int)HttpStatusCode.OK;
                }
            }
            catch
            {
                result.StatusCode = (int)HttpStatusCode.InternalServerError;
            }
            return result;
        }

        // GET api/<DestinationsController>/5
        [HttpGet("{id}")]
        public ObjectResult Get(string tripPlanningId, string cityName)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = null;

            try
            {
                result.Value = new clsDestinationListsDAL().getDestinationDAL(tripPlanningId, cityName);
                result.StatusCode = (int)HttpStatusCode.OK;
                if ((result.Value as clsTripPlanning) == null)
                {
                    result.StatusCode = (int)HttpStatusCode.NotFound;
                }
            }
            catch
            {
                result.StatusCode = (int)HttpStatusCode.InternalServerError;
            }

            return result;
        }

        // POST api/<DestinationsController>
        [HttpPost]
        public ObjectResult Post([FromBody] clsDestination destination)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int insert = 0;
            try
            {
                insert = new clsDestinationManagerDAL().insertDestination(destination);
                if (!(insert == 1))
                {
                    result.StatusCode = (int)HttpStatusCode.NotFound;
                }
                else
                {
                    result.StatusCode = (int)HttpStatusCode.OK;
                }
            }
            catch
            {
                result.StatusCode = (int)HttpStatusCode.InternalServerError;
            }
            result.Value = insert;
            return result;
        }

        // PUT api/<DestinationsController>/5
        [HttpPut("{id}")]
        public ObjectResult Put([FromBody] clsDestination destination)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int modify = 0;
            try
            {
                modify = new clsDestinationManagerDAL().modifyDestination(destination);
                if (!(modify == 1))
                {
                    result.StatusCode = (int)HttpStatusCode.NotFound;
                }
                else
                {
                    result.StatusCode = (int)HttpStatusCode.OK;
                }
            }
            catch
            {
                result.StatusCode = (int)HttpStatusCode.InternalServerError;
            }
            result.Value = modify;
            return result;
        }

        // DELETE api/<DestinationsController>/5
        [HttpDelete("{id}")]
        public ObjectResult Delete(int id)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int delete = 0;
            try
            {
                delete = new clsDestinationManagerDAL().deleteDestination(id);
                if (!(delete == 1))
                {
                    result.StatusCode = (int)HttpStatusCode.NotFound;
                }
                else
                {
                    result.StatusCode = (int)HttpStatusCode.OK;
                }
            }
            catch
            {
                result.StatusCode = (int)HttpStatusCode.InternalServerError;
            }
            result.Value = delete;
            return result;
        }
    }
}
