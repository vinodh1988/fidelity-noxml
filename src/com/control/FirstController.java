package com.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.config.IDException;
import com.model.Person;
import com.service.PersonService;
import com.utils.PersonValidator;

@Controller
public class FirstController {
@Autowired
   PersonService pservice;

@Autowired
    PersonValidator validator;

@InitBinder
protected void initBinder(WebDataBinder binder) {
   binder.addValidators(validator);
}


	 @RequestMapping("/first")
	 public String home()
	 {
	   return "home";	 
	 }
	 
	 @RequestMapping("/people")
	 public ModelAndView people(ModelMap map) {
		map.addAttribute("people",pservice.getPeople());
		return new ModelAndView("people","person",new Person());
				//ist paramater name of the view
				//2nd parameter name of the moduleAttribute
	 }
	 
	 @RequestMapping(value="/store",method=RequestMethod.POST)
	 public String storePerson
	 (@ModelAttribute("person")@Validated Person person,BindingResult result,
			 ModelMap map) {
		 try {
			if(result.hasErrors())
				return "people";
			pservice.addPerson(person); 
			return "redirect:people.asp";
		 }
		 catch(IDException e) {
			 map.addAttribute("error","ID Already Exists...!!! ERROR");
			 return "error";
		 }
		 catch(Exception e ) {
			 e.printStackTrace();
			 map.addAttribute("error","Something went wrong!!! "+e.getMessage());
			 return "error";
		 }
		 
	 }
}
