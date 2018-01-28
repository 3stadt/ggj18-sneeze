one = 0.4

define :riff do
  play_pattern_timed [:a4, :a4, :f4, :d4, :d5, :d5, :b4, :g4, :b4, :a4, :d4,:d4, :c4,:d4],[one, one*1.5,one*0.5,one,one,one*1.5,one*0.5,one,one,one,one,one,one,one*3], release: 0.4
end



define :drums do
  in_thread do
    4.times do
      sleep one
      sample :drum_heavy_kick
      sleep one
      sample :drum_snare_hard
      sleep one
      sample :drum_heavy_kick
      sleep one/2
      sample :drum_heavy_kick
      sleep one/2
      sample :drum_snare_hard
    end
  end
end

define :hihat do
  in_thread do
    4.times do
      sleep one
      sample :drum_cymbal_closed
      sleep one
      sample :drum_cymbal_pedal
    end
  end
end


define :bg do
  in_thread do
    4.times do
      sample :ambi_glass_rub, rate:0.5, attack: 0.3, release: 0.2, amp: 0.1
    end
  end
end





define :boom do
  in_thread do
    4.times do
      sleep one
      sample :bd_ada
      sleep one*2
      sample :bd_fat
      sleep one
    end
  end
end


define :boom2 do
  in_thread do
    8.times do
      sleep one
      sample :bd_haus, amp:1
      sleep one
      sample :bd_fat, amp:1
    end
  end
end


define :hi do
  in_thread do
    4.times do
      sleep one
      sample :drum_cymbal_soft, release: 0.05, amp: 0.2
      sleep one/2
      sample :drum_cymbal_soft, release: 0.05, amp: 0.2
      sleep 1.5*one
    end
  end
end




1.times do
  boom
  in_thread do
    with_synth :piano do
      riff
    end
  end
  
  with_fx :distortion,amp:0.6 do
    use_synth :fm
    riff
  end
  
end



2.times do
  boom2
  in_thread do
    with_synth :piano do
      riff
    end
  end
  with_fx :distortion, distort: 0.7 do
    with_synth :fm do
      riff
    end
  end
  
end
