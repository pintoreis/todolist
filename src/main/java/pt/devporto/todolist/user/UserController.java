package pt.devporto.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores:
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

    /**
     * String (texto)
     * Integer (int) números inteiros
     * Double (double) Números décimais 0.0000
     * Float (float) Números 0.000
     * char (A C )
     * Date (data)
     * void (quando não quer retorno)
     */
    /*
     * Body
     */

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null) {

            // System.out.println("Utilizador já existe"); removido pois não necessário com a nova configuração
            // Mensagem de erro
            // Status code

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizador já existe");
        }

            var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

            userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);

    }

}
