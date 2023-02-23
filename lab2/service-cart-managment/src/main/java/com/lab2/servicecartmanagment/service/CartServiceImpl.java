@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart addItemToCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
        cart.addItem(product);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = getCartById(cartId);
        cart.removeItem(itemId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.clearItems();
        return cartRepository.save(cart);
    }
}
