package org.cloudfoundry.community.broker.mysql.controller

import org.cloudfoundry.community.broker.mysql.service.ServiceBinding
import org.cloudfoundry.community.broker.mysql.service.ServiceBindingCredentials
import org.cloudfoundry.community.broker.mysql.service.ServiceBindingService
import org.cloudfoundry.community.broker.mysql.service.ServiceInstance
import org.cloudfoundry.community.broker.mysql.service.ServiceInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.apache.log4j.Logger;

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/13/13
 */
@Controller
@RequestMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
class ServiceBindingRestController {
  @Autowired ServiceBindingService bindingService
  // @Autowired ServiceBindingCredentials

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  ServiceBindingCredentials update(@PathVariable String instanceId, @PathVariable String bindingId) {
    ServiceBinding binding = bindingService.findById(bindingId, instanceId)
    bindingService.save(binding)
    Logger logger = Logger.getLogger(getClass());
    logger.info("in binding service contorller function, the binding was '${binding}'");
    ServiceBindingCredentials credentials = new ServiceBindingCredentials(binding.database, binding.username, binding.password)
    return credentials
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map destroy(@PathVariable String instanceId, @PathVariable String bindingId) {
    ServiceBinding binding = bindingService.findById(bindingId, instanceId)
    bindingService.destroy(binding)
    return [:]
  }

}
