package com.example.chess_masters.game;

public class ResponseMessage
{
  private PieceColor color;
  private GameState state;

  public ResponseMessage(GameState paramGameState, PieceColor paramPieceColor)
  {
    this.state = paramGameState;
    this.color = paramPieceColor;
  }

  public PieceColor getColor()
  {
    return this.color;
  }

  public GameState getState()
  {
    return this.state;
  }
}

/* Location:           C:\Users\spind\Desktop\ChessMasters_dex2jar.jar
 * Qualified Name:     com.example.chess_masters.game.ResponseMessage
 * JD-Core Version:    0.6.0
 */