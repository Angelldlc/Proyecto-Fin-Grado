﻿using CRUDOurTravel_DAL.Gestora;
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
    public class UsersController : ControllerBase
    {
        // GET: api/<UsersController>
        [HttpGet]
        public ObjectResult Get()
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = null;
            try
            {
                result.Value = new clsUserListsDAL().getCompleteUsersListDAL();
                if ((result.Value as List<clsUser>).Count == 0)
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

        // GET api/<UsersController>/5
        [HttpGet("{id}")]
        public ObjectResult Get(string id)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = null;

            try
            {
                result.Value = new clsUserListsDAL().getUserDAL(id);
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

        // POST api/<UsersController>
        [HttpPost]
        public ObjectResult Post([FromBody] clsUser user)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int insert = 0;
            try
            {
                insert = new clsUserManagerDAL().insertUser(user);
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

        // PUT api/<UsersController>/5
        [HttpPut("{id}")]
        public ObjectResult Put([FromBody] clsUser user)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int modify = 0;
            try
            {
                modify = new clsUserManagerDAL().modifyUser(user);
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

        // DELETE api/<UsersController>/5
        [HttpDelete("{id}")]
        public ObjectResult Delete(int id)
        {
            ObjectResult result = new ObjectResult(new { });
            result.Value = 0;
            int delete = 0;
            try
            {
                delete = new clsUserManagerDAL().deleteUser(id);
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