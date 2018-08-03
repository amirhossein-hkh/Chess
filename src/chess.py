from board import Board
from player import Player
from chesspiece import Color

if __name__ == '__main__':
    player1 = Player('player1', Color.WHITE)
    player2 = Player('player2', Color.BLACK)
    board = Board(player1, player2)
    white_turn = True
    while True:
        color = Color.WHITE if white_turn else Color.BLACK
        player = player1 if white_turn else player2
        board.print_board()

        move_succeeded = False
        while not move_succeeded:
            if not board.is_any_move_left(color):
                if player.check:
                    print('{} checkmate'.format(color.name))
                    break
                else:
                    print('it is a Stalemate and the game end in a draw')
                    break
            start_row, start_col = player.get_start_input()
            if board.grid[start_row][start_col] is not None and board.grid[start_row][start_col].color == color:
                print(board.grid[start_row][start_col].generate_possible_moves())
                end_row, end_col = player.get_move_input()
                move_succeeded = board.move(start_row, start_col, end_row, end_col, color)
                if not move_succeeded:
                    print('this move can not be made!')
            else:
                print('choose a valid square')
        white_turn = not white_turn
