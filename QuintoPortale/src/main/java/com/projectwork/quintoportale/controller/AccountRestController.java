package com.projectwork.quintoportale.controller;

import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectwork.quintoportale.model.Account;
import com.projectwork.quintoportale.model.AccountRepository;
import com.projectwork.quintoportale.view.AccountDTO;
import com.projectwork.quintoportale.view.Login;
import com.projectwork.quintoportale.view.UpdateAccountDTO;
import com.projectwork.quintoportale.view.UpdatePasswordDTO;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	
	@Autowired
	AccountRepository accRepo;
	
	@PostMapping
	public int create(
			@RequestBody
			AccountDTO dto) {
		Optional<Account> optEmail= accRepo.findByEmail(dto.getEmail());
		Optional<Account> optUsername= accRepo.findByUsername(dto.getUsername());
		if(optEmail.isPresent() 
				|| optUsername.isPresent()
				|| !dto.getEmail().contains("@") 
				|| !dto.getEmail().contains(".")
				|| dto.getEmail().equals("")
				|| dto.getUsername().equals("")
				|| dto.getName().equals("")
				|| dto.getPassword().equals("")
				|| dto.getSurname().equals("")
				|| dto.getEmail()== null
				|| dto.getUsername()== null
				|| dto.getName()== null
				|| dto.getPassword()== null
				|| dto.getSurname()== null
				){
			return -1;
		}
		String encPsw = encryptPassword(dto.getPassword());
		Account account = new Account(
				dto.getUsername(), 
				encPsw,
				dto.getName(),
				dto.getSurname(),
				dto.getEmail()
		);
		/*
		 * Account account = new Account();
		BeanUtils.copyProperties(dto, account); //Betto TOP Player NON UTILIZZABILE PER MANCANZA DI OMONIMIA
		*/
		return accRepo.save(account).getId();
	}
	
	@GetMapping("/account/{accountId}")
	public AccountDTO readOne(
			@PathVariable
			int accountId) {
		Optional<Account> opt = accRepo.findById(accountId);
		if(opt.isPresent()) {
			Account account = opt.get();
			AccountDTO accountDto = new AccountDTO(
					account.getUsername(),
					account.getEncryptedPassword(),
					account.getName(),
					account.getSurname(),
					account.getEmail()
			);
			return accountDto;
		}
		return null;
	}
	
	@PutMapping("/updateInfo/{id}")
	public boolean updateInfo(
			@PathVariable
			int id,
			@RequestBody
			UpdateAccountDTO dto) {
		Optional<Account> optAccountUsername = accRepo.findByUsername(dto.getUsername());
		Optional<Account> optAccountEmail = accRepo.findByEmail(dto.getEmail());
		if(optAccountUsername.isPresent() || optAccountEmail.isPresent() || 
			((dto.getEmail().equals("") && dto.getUsername().equals("")))) {
			return false;
		}
		Optional<Account> opt = accRepo.findById(id);
		if(opt.isPresent()) {
			Account oldAccount = opt.get();
			if(!dto.getEmail().equals("") && dto.getEmail().contains("@") && dto.getEmail().contains(".")) {
				oldAccount.setEmail(dto.getEmail());
			}
			if(!dto.getUsername().equals("")) {
				oldAccount.setUsername(dto.getUsername());
			}
			accRepo.save(oldAccount);
			return true;
		}
		return false;
	}
	
	@PutMapping("/updatePsw/{id}")
	public boolean updatePsw(
			@PathVariable
			int id,
			@RequestBody
			UpdatePasswordDTO dto) {
		Optional<Account> opt = accRepo.findById(id);
		if(opt.isPresent()) {
			Account account = opt.get();
			if(account.getEncryptedPassword().equals(dto.getOldPassword())) {
				account.setEncryptedPassword(encryptPassword(dto.getNewPassword()));
				return true;
			}
		}
		return false;
	}
	
	@DeleteMapping("/{id}")
	public void deleteOne(
			@PathVariable
			int id) {
		accRepo.deleteById(id);
	}
	
	@PostMapping("/login")
	public boolean tryToLog(
			@RequestBody
			Login dto) {
		String username = dto.getUsername();
		String psw = encryptPassword(dto.getPassword());
		Optional<Account> opt = accRepo.findByUsername(username);
		if(opt.isPresent()) {
			if(opt.get().getEncryptedPassword().equals(psw)) {
				return true;
			}
		}
		return false;
	}	
	
	private String encryptPassword(String password) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");

			/* Add plain-text password bytes to digest using MD5 update() method. */
			m.update(password.getBytes());

			/* Convert the hash value into bytes */
			byte[] bytes = m.digest();

			/*
			 * The bytes array has bytes in decimal form. Converting it into hexadecimal
			 * format.
			 */
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			/* Complete hashed password in hexadecimal format */
			String encryptedpassword = s.toString();

			return encryptedpassword;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
