package mate.academy.bookshop.service.shoppingcart;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.cartitem.CartItemRequestDto;
import mate.academy.bookshop.dto.cartitem.CartItemUpdateDto;
import mate.academy.bookshop.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.bookshop.exceptions.EntityNotFoundException;
import mate.academy.bookshop.mapper.CartItemMapper;
import mate.academy.bookshop.mapper.ShoppingCartMapper;
import mate.academy.bookshop.model.CartItem;
import mate.academy.bookshop.model.ShoppingCart;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.repository.CartItemRepository;
import mate.academy.bookshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(Long userId) {
        ShoppingCart shoppingCartByUserId = getShoppingCartByUserId(userId);
        return shoppingCartMapper.toDto(shoppingCartByUserId);
    }

    @Override
    public ShoppingCartResponseDto addCartItem(CartItemRequestDto cartItemUpdateDto, Long userId) {
        ShoppingCart shoppingCartByUserId = getShoppingCartByUserId(userId);

        Optional<CartItem> existingCartItem = shoppingCartByUserId
                .getCartItems()
                .stream()
                .filter(cartItem -> cartItem
                        .getBook()
                        .getId()
                        .equals(cartItemUpdateDto.getBookId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.orElseThrow();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemUpdateDto.getQuantity());
        } else {
            CartItem cartItem = cartItemMapper.toEntity(cartItemUpdateDto);
            cartItem.setShoppingCart(shoppingCartByUserId);
            shoppingCartByUserId.getCartItems().add(cartItem);
        }
        shoppingCartRepository.save(shoppingCartByUserId);
        return shoppingCartMapper.toDto(shoppingCartByUserId);
    }

    @Override
    public ShoppingCartResponseDto updateCartItems(Long userId,
                                                   Long cartItemId,
                                                   CartItemUpdateDto updateDto) {
        ShoppingCart shoppingCartByUserId = getShoppingCartByUserId(userId);

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCartByUserId.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item"
                        + " with id: " + cartItemId));

        cartItemMapper.updateCartItemFromDto(updateDto, cartItem);
        return shoppingCartMapper.toDto(shoppingCartByUserId);
    }

    @Override
    public void deleteCartItemFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCartByUserId = getShoppingCartByUserId(userId);

        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCartByUserId.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item"
                        + " with id: " + cartItemId));
        shoppingCartByUserId.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find shopping cart"
                        + " by user id: " + userId));
        return shoppingCart;
    }
}
